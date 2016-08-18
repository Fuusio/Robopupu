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
import com.robopupu.feature.main.presenter.SplashPresenter;
import com.robopupu.feature.main.view.MainView;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;
import com.robopupu.api.feature.AbstractFeature;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;

@Plugin
public class MainFeatureImpl extends AbstractFeature implements MainFeature {

    private boolean splashShown;

    @Plug MainView mainView;

    @Scope(RobopupuAppScope.class)
    @Provides(MainFeature.class)
    public MainFeatureImpl() {
        super(MainFeatureScope.class, true);
        splashShown = false;
    }

    @Override
    public void onFeatureContainerStarted(final FeatureContainer container) {
        super.onFeatureContainerStarted(container);

        if (splashShown) {
            openNavigationDrawer();
        }

    }

    @Override
    public void onMainPresenterStarted() {

        if (!splashShown) {
            splashShown = true;
            showView(mainView.getMainFeatureContainer(), SplashPresenter.class, false, null);
        }
    }

    @Override
    public void onHideSplashView() {
        hideView(SplashPresenter.class, false, null);
    }

    @Override
    public void openNavigationDrawer() {
        mainView.openNavigationDrawer();
    }
}
