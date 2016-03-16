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

import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.app.error.RobopupuAppError;
import com.robopupu.component.AppManager;
import com.robopupu.component.PlatformManager;
import com.robopupu.component.TimerManager;

import com.robopupu.api.app.BaseApplication;
import com.robopupu.api.dependency.AppDependencyScope;
import com.robopupu.api.plugin.PluginBus;

public class RobopupuApplication extends BaseApplication {

    // Google Analytics Property ID
    public final static int PROPERTY_ID = 0;

    public RobopupuApplication() {
        super();
    }

    @Override
    protected AppDependencyScope createAppScope() {
        return new RobopupuAppScope(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RobopupuAppError.setContext(getApplicationContext());
    }

    /**
     * Gets the Google Analytics Property ID.
     * @return The property ID as an {@code int} value.
     */
    public int getAnalyticsPropertyId() {
        return PROPERTY_ID;
    }

    @Override
    protected void configureApplication() {
        super.configureApplication();

        PluginBus.plug(AppManager.class);
        PluginBus.plug(PlatformManager.class);
        PluginBus.plug(TimerManager.class);

        final PluginFeatureManager featureManager = PluginBus.plug(PluginFeatureManager.class);
        registerActivityLifecycleCallbacks(featureManager.getActivityLifecycleCallback());
    }
}
