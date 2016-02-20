/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://fuusio.org
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
package org.fuusio.api.app;

import org.fuusio.api.dependency.AppDependencyScope;
import org.fuusio.api.dependency.DependenciesCache;
import org.fuusio.api.feature.FeatureManager;
import org.fuusio.api.feature.AbstractFeatureManager;
import org.fuusio.api.graphics.BitmapManager;
import org.fuusio.api.graphics.BitmapManagerImpl;
import org.fuusio.api.network.RequestManager;
import org.fuusio.api.network.volley.VolleyRequestManager;
import org.fuusio.api.plugin.PluginBus;
import org.fuusio.api.util.PermissionRequestManager;

public abstract class FuusioAppScope extends AppDependencyScope<FuusioApplication> {

    protected FuusioAppScope(final FuusioApplication application) {
        super(application);
    }

    @Override
    protected <T> T getDependency() {
        if (type(PluginBus.class)) {
            return dependency(PluginBus.getInstance());
        } else if (type(BitmapManager.class)) {
            return dependency(new BitmapManagerImpl());
        } else if (type(DependenciesCache.class)) {
            return dependency(getApplication().getDependenciesCache());
        } else if (type(RequestManager.class)) {
            return dependency(new VolleyRequestManager());
        } else if (type(PermissionRequestManager.class)) {
            return dependency(new PermissionRequestManager());
        }
        return super.getDependency();
    }
}
