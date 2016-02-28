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
package com.robopupu.feature.feedback.presenter;

import com.robopupu.component.TimerHandle;
import com.robopupu.component.TimerManager;
import com.robopupu.feature.feedback.view.ThxView;
import com.robopupu.feature.main.MainFeature;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.AbstractFeaturePresenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;

@Plugin
public class ThxPresenterImpl extends AbstractFeaturePresenter<ThxView>
        implements ThxPresenter {

    @Plug MainFeature mMainFeature;
    @Plug TimerManager mTimerManager;
    @Plug ThxView mView;

    @Provides(ThxPresenter.class)
    public ThxPresenterImpl() {
    }

    @Override
    public ThxView getViewPlug() {
        return mView;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        plug(ThxView.class);
    }

    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);

        mTimerManager.createTimer(new TimerManager.Callback() {
            @Override
            public void timeout(TimerHandle handle) {
                mMainFeature.openNavigationDrawer();
            }
        }, 3000L);
    }
}
