/*
 * Copyright (C) 2016 Marko Salmela, http://fuusio.org
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

import org.fuusio.api.mvp.Presenter;

/**
 * {@link DelegatedWebViewClient} extends {@link WebViewClient} to provide support
 * for using it in the MVP architectural design pattern.  {@link DelegatedWebViewClient} uses
 * a delegate for some of the control functions. Typically a {@link Presenter} implements
 * the {@link com.robopupu.app.view.DelegatedWebViewClient.Delegate} interface.
 */
public class DelegatedWebViewClient extends WebViewClient {

    private static final String TAG = DelegatedWebViewClient.class.getSimpleName();

    private Delegate mDelegate;
    private String mFailingUrl;
    private boolean mLoadingFailed;
    private boolean mLoadingFinished;
    private boolean mRetryingLoading;

    public DelegatedWebViewClient(final Delegate delegate) {
        if (delegate == null) {
            throw new IllegalArgumentException("A Delegate may not be null");
        }
        mDelegate = delegate;
    }

    public final boolean isRetryingLoading() {
        return mRetryingLoading;
    }

    public void setRetryingLoading(final boolean retrying) {
        mRetryingLoading = retrying;
    }

    @Override
    public boolean shouldOverrideUrlLoading(final WebView webView, final String url) {
        if (mDelegate.shouldLoadUrl(webView, url)) {
            webView.loadUrl(url);
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReceivedError(final WebView webView, final int errorCode, final String description, final String failingUrl) {
        super.onReceivedError(webView, errorCode, description, failingUrl);

        Log.d(TAG, "onReceivedError(...) : Failed to load URL : " + failingUrl + ". Reason: " + description + " Error code: " + errorCode);

        mFailingUrl = failingUrl;
        mLoadingFailed = true;

        switch (errorCode) {
            case WebViewClient.ERROR_CONNECT:
            case WebViewClient.ERROR_HOST_LOOKUP: {
                mDelegate.onUrlLoadingFailed(webView, mFailingUrl, errorCode);
                break;
            }
            case WebViewClient.ERROR_UNSUPPORTED_SCHEME:
            case WebViewClient.ERROR_BAD_URL: {
                mDelegate.onUrlLoadingFailed(webView, mFailingUrl, errorCode);
                break;
            }
            default: {
                mDelegate.onUrlLoadingFailed(webView, mFailingUrl, errorCode);
                break;
            }
        }
        super.onReceivedError(webView, errorCode, description, failingUrl);
    }

    @Override
    public void onPageFinished(final WebView webView, final String url) {
        super.onPageFinished(webView, url);

        mLoadingFinished = true;

        if (!mLoadingFailed && mRetryingLoading) {
            if (mFailingUrl != null) {
                if (mFailingUrl.contentEquals(url)) {
                    mDelegate.onUrlReloadingSucceeded(webView, url);
                    mFailingUrl = null;
                }
            }
        } else {
            mDelegate.onUrlLoadingSucceeded(webView, url);
        }
    }

    @Override
    public void onPageStarted(final WebView webView, final String url, final Bitmap favicon) {
        
        if (mRetryingLoading) {
            if (mFailingUrl != null) {
                if (mFailingUrl.equalsIgnoreCase(url) && mLoadingFinished) {
                    mLoadingFailed = false;
                }
            }
        }
        mLoadingFinished = false;

        super.onPageStarted(webView, url, favicon);
    }

    public interface Delegate {
        boolean shouldLoadUrl(final WebView webView, final String url);
        void onUrlLoadingFailed(final WebView webView, final String url, final int errorCode);
        void onUrlLoadingSucceeded(final WebView webView, final String url);
        void onUrlReloadingSucceeded(final WebView webView, final String url);
    }
}
