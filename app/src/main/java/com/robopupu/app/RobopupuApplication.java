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
package com.robopupu.app;

import com.robopupu.api.app.Robopupu;
import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.app.error.RobopupuAppError;
import com.robopupu.component.AppManager;
import com.robopupu.component.PlatformManager;
import com.robopupu.component.TimerManager;

import com.robopupu.api.app.BaseApplication;
import com.robopupu.api.dependency.AppDependencyScope;
import com.robopupu.api.plugin.PluginBus;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class RobopupuApplication extends BaseApplication {

    // Google Analytics Property ID
    public final static int PROPERTY_ID = 0;

    private RefWatcher mRefWatcher;
    private Robopupu mRobopupu;

    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher = LeakCanary.install(this);
        RobopupuAppError.setContext(getApplicationContext());
    }

    @Override
    public int getAnalyticsPropertyId() {
        return PROPERTY_ID;
    }

    @Override
    protected void configureApplication() {
        final AppDependencyScope appScope = new RobopupuAppScope(this);

        mRobopupu = new Robopupu(appScope);

        PluginBus.plug(AppManager.class);
        PluginBus.plug(PlatformManager.class);
        PluginBus.plug(TimerManager.class);

        final PluginFeatureManager featureManager = PluginBus.plug(PluginFeatureManager.class);
        registerActivityLifecycleCallbacks(featureManager.getActivityLifecycleCallback());
    }
}
