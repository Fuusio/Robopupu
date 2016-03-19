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
package com.robopupu.feature.main;

import com.robopupu.api.feature.FeatureContainer;
import com.robopupu.app.RobopupuAppScope;
import com.robopupu.feature.main.presenter.MainPresenter;
import com.robopupu.feature.main.presenter.SplashPresenter;
import com.robopupu.feature.main.view.MainView;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;
import com.robopupu.api.feature.AbstractFeature;
import com.robopupu.api.mvp.Presenter;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;

@Plugin
public class MainFeatureImpl extends AbstractFeature implements MainFeature {

    private boolean mSplashShown;

    @Plug MainView mMainView;

    @Scope(RobopupuAppScope.class)
    @Provides(MainFeature.class)
    public MainFeatureImpl() {
        super(MainFeatureScope.class, true);
        mSplashShown = false;
    }

    @Override
    public void onFeatureContainerStarted(final FeatureContainer container) {
        super.onFeatureContainerStarted(container);

        if (mSplashShown) {
            openNavigationDrawer();
        }

    }

    @Override
    public void onPresenterStarted(final Presenter presenter) {

        if (presenter instanceof MainPresenter) {
            if (!mSplashShown) {
                mSplashShown = true;
                showView(mMainView.getMainFeatureContainer(), SplashPresenter.class, false);
            }
        }
    }

    @Override
    public void openNavigationDrawer() {
        mMainView.openNavigationDrawer();
    }
}
