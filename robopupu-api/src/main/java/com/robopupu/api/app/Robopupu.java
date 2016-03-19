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

import com.robopupu.api.dependency.AppDependencyScope;
import com.robopupu.api.dependency.DependenciesCache;
import com.robopupu.api.dependency.Dependency;
import com.robopupu.api.util.UIToolkit;

public class Robopupu {

    private static final String TAG = Robopupu.class.getSimpleName();

    private static Robopupu sInstance = null;

    protected final Application mApplication;
    protected final AppDependencyScope mAppScope;
    protected final DependenciesCache mDependenciesCache;

    public Robopupu(final AppDependencyScope appScope) {
        mApplication = appScope.getApplication();
        mDependenciesCache = createDependencyScopeCache();
        mAppScope = appScope;
        mAppScope.initialize();

        setInstance(this);

        UIToolkit.setApplication(mApplication);
        Dependency.setAppScope(mAppScope);
    }

    public DependenciesCache getDependenciesCache() {
        return mDependenciesCache;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Robopupu> T getInstance() {
        return (T) sInstance;
    }

    private static void setInstance(final Robopupu instance) {
        sInstance = instance;
    }

    protected DependenciesCache createDependencyScopeCache() {
        return new DependenciesCache();
    }
}
