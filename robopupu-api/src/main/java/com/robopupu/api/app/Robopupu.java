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
import com.robopupu.api.util.Utils;

public class Robopupu {

    private static final String TAG = Utils.tag(Robopupu.class);

    private static Robopupu instance = null;

    protected final Application application;
    protected final AppDependencyScope appScope;
    protected final DependenciesCache dependenciesCache;

    public Robopupu(final AppDependencyScope appScope) {
        application = appScope.getApplication();
        dependenciesCache = createDependencyScopeCache();
        this.appScope = appScope;
        this.appScope.initialize();

        setInstance(this);

        UIToolkit.setApplication(application);
        Dependency.setAppScope(this.appScope);
    }

    public DependenciesCache getDependenciesCache() {
        return dependenciesCache;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Robopupu> T getInstance() {
        return (T) instance;
    }

    private static void setInstance(final Robopupu instance) {
        Robopupu.instance = instance;
    }

    protected DependenciesCache createDependencyScopeCache() {
        return new DependenciesCache();
    }
}
