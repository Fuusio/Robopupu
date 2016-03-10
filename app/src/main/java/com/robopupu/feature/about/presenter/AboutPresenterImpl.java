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

import com.robopupu.component.AppManager;
import com.robopupu.feature.about.view.AboutView;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.AbstractFeaturePresenter;
import com.robopupu.api.mvp.View;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.plugin.PluginBus;

@Plugin
@Provides(AboutPresenter.class)
public class AboutPresenterImpl extends AbstractFeaturePresenter<AboutView>
        implements AboutPresenter {

    @Plug AppManager mAppManager;
    @Plug AboutPresenterListener mListener;
    @Plug AboutView mView;

    @Override
    public AboutView getViewPlug() {
        return mView;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus);
        plug(AboutView.class);
    }

    @Override
    public void onViewResume(final View view) {
        super.onViewResume(view);
        mView.setVersionText(mAppManager.getAppVersionName());
    }

    @Override
    public void onViewLicenseClick() {
        mListener.onShowLicenseInfo();
    }

    @Override
    public void onViewOssLicensesClick() {
        mListener.onShowOssLicensesInfo();
    }

    @Override
    public void onViewSourcesClick() {
        mListener.onOpenSourcesWebPage();
    }
}
