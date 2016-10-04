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
package com.robopupu.feature.feedback.presenter;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.AbstractFeaturePresenter;
import com.robopupu.api.mvp.View;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.component.TimerHandle;
import com.robopupu.component.TimerManager;
import com.robopupu.feature.feedback.view.ThxView;
import com.robopupu.feature.main.MainFeature;

@Plugin
public class ThxPresenterImpl extends AbstractFeaturePresenter<ThxView>
        implements ThxPresenter {

    @Plug MainFeature mainFeature;
    @Plug TimerManager timerManager;
    @Plug ThxView view;

    @Provides(ThxPresenter.class)
    public ThxPresenterImpl() {
    }

    @Override
    public ThxView getViewPlug() {
        return view;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus);
        plug(ThxView.class);
    }

    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);

        timerManager.createTimer(new TimerManager.Callback() {
            @Override
            public void timeout(TimerHandle handle) {
                mainFeature.openNavigationDrawer();
            }
        }, 3000L);
    }
}
