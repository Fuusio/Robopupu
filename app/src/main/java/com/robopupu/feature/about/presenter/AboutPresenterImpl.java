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
package com.robopupu.feature.about.presenter;

import com.robopupu.R;
import com.robopupu.component.AppManager;
import com.robopupu.feature.about.AboutFeature;
import com.robopupu.feature.about.view.AboutView;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.AbstractFeaturePresenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;

@Plugin
public class AboutPresenterImpl extends AbstractFeaturePresenter<AboutView>
        implements AboutPresenter {

    //@Plug AboutFeature mFeature;
    @Plug AboutPresenterListener mListener;
    @Plug AppManager mAppManager;
    @Plug AboutView mView;

    @Provides(AboutPresenter.class)
    public AboutPresenterImpl() {
    }

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
    public void onViewStart(final View view) {
        super.onViewStart(view);

        mView.setVersionText(mAppManager.getAppVersionName());
    }

    @Override
    public void onViewLicenseTextClicked() {
        mListener.onShowLicenseInfo();
        //mFeature.showLicenseInfo();
    }

    @Override
    public void onViewOssLicensesTextClicked() {
        mListener.onShowOssLicensesInfo();
        //mFeature.showOssLicensesInfo();
    }

    @Override
    public void onSourcesClicked() {
        mListener.onOpenSourcesWebPage();
        //mFeature.openSourcesWebPage();
    }
}
