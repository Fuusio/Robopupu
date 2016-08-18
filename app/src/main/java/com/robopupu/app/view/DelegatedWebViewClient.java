/*
 * Copyright (C) 2016 Marko Salmela, http://robopupu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robopupu.app.view;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.robopupu.api.mvp.Presenter;
import com.robopupu.api.util.Utils;

/**
 * {@link DelegatedWebViewClient} extends {@link WebViewClient} to provide support
 * for using it in the MVP architectural design pattern.  {@link DelegatedWebViewClient} uses
 * a delegate for some of the control functions. Typically a {@link Presenter} implements
 * the {@link com.robopupu.app.view.DelegatedWebViewClient.Delegate} interface.
 */
public class DelegatedWebViewClient extends WebViewClient {

    private static final String TAG = Utils.tag(DelegatedWebViewClient.class);

    private Delegate delegate;
    private String failingUrl;
    private boolean loadingFailed;
    private boolean loadingFinished;
    private boolean retryingLoading;

    public DelegatedWebViewClient(final Delegate delegate) {
        if (delegate == null) {
            throw new IllegalArgumentException("A Delegate may not be null");
        }
        this.delegate = delegate;
    }

    public final boolean isRetryingLoading() {
        return retryingLoading;
    }

    public void setRetryingLoading(final boolean retrying) {
        retryingLoading = retrying;
    }

    @Override
    public boolean shouldOverrideUrlLoading(final WebView webView, final String url) {
        if (delegate.shouldLoadUrl(webView, url)) {
            webView.loadUrl(url);
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReceivedError(final WebView webView, final int errorCode, final String description, final String failingUrl) {
        super.onReceivedError(webView, errorCode, description, failingUrl);

        Log.d(TAG, "onReceivedError(...) : Failed to load URL : " + failingUrl + ". Reason: " + description + " Error code: " + errorCode);

        this.failingUrl = failingUrl;
        loadingFailed = true;

        switch (errorCode) {
            case WebViewClient.ERROR_CONNECT:
            case WebViewClient.ERROR_HOST_LOOKUP: {
                delegate.onUrlLoadingFailed(webView, this.failingUrl, errorCode);
                break;
            }
            case WebViewClient.ERROR_UNSUPPORTED_SCHEME:
            case WebViewClient.ERROR_BAD_URL: {
                delegate.onUrlLoadingFailed(webView, this.failingUrl, errorCode);
                break;
            }
            default: {
                delegate.onUrlLoadingFailed(webView, this.failingUrl, errorCode);
                break;
            }
        }
        super.onReceivedError(webView, errorCode, description, failingUrl);
    }

    @Override
    public void onPageFinished(final WebView webView, final String url) {
        super.onPageFinished(webView, url);

        loadingFinished = true;

        if (!loadingFailed && retryingLoading) {
            if (failingUrl != null) {
                if (failingUrl.contentEquals(url)) {
                    delegate.onUrlReloadingSucceeded(webView, url);
                    failingUrl = null;
                }
            }
        } else {
            delegate.onUrlLoadingSucceeded(webView, url);
        }
    }

    @Override
    public void onPageStarted(final WebView webView, final String url, final Bitmap favicon) {
        
        if (retryingLoading) {
            if (failingUrl != null) {
                if (failingUrl.equalsIgnoreCase(url) && loadingFinished) {
                    loadingFailed = false;
                }
            }
        }
        loadingFinished = false;

        super.onPageStarted(webView, url, favicon);
    }

    public interface Delegate {
        boolean shouldLoadUrl(final WebView webView, final String url);
        void onUrlLoadingFailed(final WebView webView, final String url, final int errorCode);
        void onUrlLoadingSucceeded(final WebView webView, final String url);
        void onUrlReloadingSucceeded(final WebView webView, final String url);
    }
}
