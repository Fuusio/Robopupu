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
package com.robopupu.feature.main.presenter;

import com.robopupu.R;
import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.api.mvp.View;
import com.robopupu.component.AppManager;
import com.robopupu.feature.about.AboutFeature;
import com.robopupu.feature.feedback.FeedbackFeature;
import com.robopupu.feature.fsm.FsmDemoFeature;
import com.robopupu.feature.jokes.JokesFeature;
import com.robopupu.feature.main.MainFeature;
import com.robopupu.feature.main.view.MainView;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.FeatureContainer;
import com.robopupu.api.mvp.AbstractPresenter;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.feature.multipleviews.MultipleViewsFeature;
import com.robopupu.feature.viewdelegate.ViewDelegateFeature;

@Plugin
public class MainPresenterImpl extends AbstractPresenter<MainView>
        implements MainPresenter {

    private boolean wasPaused;

    @Plug AppManager appManager;
    @Plug PluginFeatureManager featureManager;
    @Plug MainFeature mainFeature;
    @Plug MainView view;

    @Provides(MainPresenter.class)
    public MainPresenterImpl() {
        wasPaused = false;
    }

    @Override
    public MainView getViewPlug() {
        return view;
    }

    @Override
    public void onBackPressed() {
        if (!featureManager.onBackPressed()) {
            appManager.exitApplication();
        }
    }

    @Override
    public boolean onNavigationItemSelected(final int itemId) {
        boolean selectionHandled = true;

        final FeatureContainer container = view.getMainFeatureContainer();

        if (itemId == R.id.navigation_about) {
            featureManager.startFeature(container, AboutFeature.class);
        } else if (itemId == R.id.navigation_feedback) {
            featureManager.startFeature(container, FeedbackFeature.class);
        } else if (itemId == R.id.navigation_fsm_demo) {
            featureManager.startFeature(container, FsmDemoFeature.class);
        } else if (itemId == R.id.navigation_jokes) {
            featureManager.startFeature(container, JokesFeature.class);
        } else if (itemId == R.id.navigation_settings) {
            //featureManager.startFeature(container, SettingsFeature.class);
        } else if (itemId == R.id.navigation_multiple_views) {
            featureManager.startFeature(container, MultipleViewsFeature.class);
        } else if (itemId == R.id.navigation_view_delegate) {
            featureManager.startFeature(container, ViewDelegateFeature.class);
        } else if (itemId == R.id.navigation_exit) {
            appManager.exitApplication();
        }else {
            selectionHandled = false;
        }
        return selectionHandled;
    }


    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);
        mainFeature.onMainPresenterStarted();
    }

    @Override
    public void onViewResume(final View view) {
        super.onViewResume(view);

        if (wasPaused) {
            wasPaused = false;
            this.view.openNavigationDrawer();
        }
    }

    @Override
    public void onViewPause(final View view) {
        super.onViewPause(view);
        wasPaused = true;
    }
}
