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
package com.robopupu.feature.main.presenter;

import com.robopupu.component.TimerHandle;
import com.robopupu.component.TimerManager;
import com.robopupu.feature.main.MainFeature;
import com.robopupu.feature.main.view.SplashView;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.AbstractFeaturePresenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;

/**
 * {@link SplashPresenterImpl} ...
 */
@Plugin
public class SplashPresenterImpl extends AbstractFeaturePresenter<SplashView>
        implements SplashPresenter {

    @Plug MainFeature mMainFeature;
    @Plug TimerManager mTimerManager;
    @Plug SplashView mView;

    @Provides(SplashPresenter.class)
    public SplashPresenterImpl() {
    }

    @Override
    public SplashView getViewPlug() {
        return mView;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        plug(SplashView.class);
    }

    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);
        mTimerManager.createTimer(new TimerManager.Callback() {
            @Override
            public void timeout(TimerHandle handle) {
                mMainFeature.openNavigationDrawer();
            }
        }, 4000L);
    }
}
