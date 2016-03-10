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
import com.robopupu.component.AppManager;
import com.robopupu.feature.about.AboutFeature;
import com.robopupu.feature.feedback.FeedbackFeature;
import com.robopupu.feature.fsm.FsmDemoFeature;
import com.robopupu.feature.main.view.MainView;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.FeatureContainer;
import com.robopupu.api.mvp.AbstractPresenter;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;

@Plugin
public class MainPresenterImpl extends AbstractPresenter<MainView>
        implements MainPresenter {

    @Plug AppManager mAppManager;
    @Plug PluginFeatureManager mFeatureManager;
    @Plug MainView mView;

    @Provides(MainPresenter.class)
    public MainPresenterImpl() {
    }

    @Override
    public MainView getViewPlug() {
        return mView;
    }

    @Override
    public void onBackPressed() {
        if (!mFeatureManager.onBackPressed()) {
            mAppManager.exitApplication();
        }
    }

    @Override
    public boolean onNavigationItemSelected(final int itemId) {
        boolean selectionHandled = true;

        final FeatureContainer container = mView.getMainFeatureContainer();

        if (itemId == R.id.navigation_about) {
            mFeatureManager.startFeature(container, AboutFeature.class);
        } else if (itemId == R.id.navigation_feedback) {
            mFeatureManager.startFeature(container, FeedbackFeature.class);
        } else if (itemId == R.id.navigation_fsm_demo) {
            mFeatureManager.startFeature(container, FsmDemoFeature.class);
        } else if (itemId == R.id.navigation_settings) {
            //mFeatureManager.startFeature(container, SettingsFeature.class);
        } else if (itemId == R.id.navigation_exit) {
            mAppManager.exitApplication();
        }else {
            selectionHandled = false;
        }
        return selectionHandled;
    }
}
