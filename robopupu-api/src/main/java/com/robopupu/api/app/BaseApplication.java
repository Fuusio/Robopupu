/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.app;

import android.app.Application;
import android.content.res.Resources;

import com.robopupu.api.util.Utils;

public abstract class BaseApplication extends Application {

    private static final String TAG = Utils.tag(BaseApplication.class);

    private static BaseApplication instance = null;

    protected BaseApplication() {
        setInstance(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        configureApplication();
    }

    /**
     * This methods should be overridden by the subclasses to configure the application. e.g., by
     * instantiating the plugin components.
     */
    protected abstract void configureApplication();

    @SuppressWarnings("unchecked")
    public static <T extends BaseApplication> T getInstance() {
        return (T) instance;
    }

    private static void setInstance(final BaseApplication instance) {
        BaseApplication.instance = instance;
    }

    /**
     * Return the Google Analytics Property ID.
     *
     * @return The property ID as an {@code int} value.
     */
    public int getAnalyticsPropertyId() {
        return -1;
    }

    /**
     * Return the {@link Resources}.
     *
     * @return A {@link Resources}.
     */
    public static Resources getApplicationResources() {
        return instance.getResources();
    }
}
