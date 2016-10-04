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

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.AbstractFeaturePresenter;
import com.robopupu.api.mvp.View;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.component.TimerHandle;
import com.robopupu.component.TimerManager;
import com.robopupu.feature.main.MainFeature;
import com.robopupu.feature.main.view.SplashView;

@Plugin
public class SplashPresenterImpl extends AbstractFeaturePresenter<SplashView>
        implements SplashPresenter {

    @Plug MainFeature mainFeature;
    @Plug TimerManager timerManager;
    @Plug SplashView view;

    @Provides(SplashPresenter.class)
    public SplashPresenterImpl() {
    }

    @Override
    public SplashView getViewPlug() {
        return view;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus);
        plug(SplashView.class);
    }

    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);

        timerManager.createTimer(new TimerManager.Callback() {
            @Override
            public void timeout(TimerHandle handle) {
                mainFeature.onHideSplashView();
                mainFeature.openNavigationDrawer();
            }
        }, 3000L);
    }
}
