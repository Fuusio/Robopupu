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
package com.robopupu.feature.about.presenter;

import android.webkit.WebView;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.AbstractFeaturePresenter;
import com.robopupu.api.mvp.View;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.feature.about.view.LicensesInfoView;

@Plugin
public class LicensesInfoPresenterImpl extends AbstractFeaturePresenter<LicensesInfoView>
        implements LicensesInfoPresenter {

    private String licensesFileUrl;

    @Plug LicensesInfoView view;

    @Provides(LicensesInfoPresenter.class)
    public LicensesInfoPresenterImpl() {
    }

    @Override
    public LicensesInfoView getViewPlug() {
        return view;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus);
        plug(LicensesInfoView.class);
    }

    @Override
    public void onViewResume(final View view) {
        super.onViewResume(view);
        licensesFileUrl = getParams().getString(KEY_PARAM_LICENSE_URL);
        this.view.loadUrl(licensesFileUrl);
    }

    @Override
    public boolean shouldLoadUrl(final WebView webView, final String url) {
        return licensesFileUrl.contentEquals(url);
    }

    @Override
    public void onUrlLoadingFailed(final WebView webView, final String url, final int errorCode) {
        // Do nothing
    }

    @Override
    public void onUrlLoadingSucceeded(final WebView webView, final String url) {
        // Do nothing
    }

    @Override
    public void onUrlReloadingSucceeded(final WebView webView, final String url) {
        // Do nothing
    }

    @Override
    public void onOkButtonClick() {
        view.dismiss();
    }
}
