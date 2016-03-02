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
package com.robopupu.api.feature;

import com.robopupu.app.RobopupuAppScope;
import com.robopupu.app.RobopupuApplication;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.feature.AbstractFeatureManager;
import org.fuusio.api.feature.Feature;
import org.fuusio.api.feature.FeatureContainer;
import org.fuusio.api.plugin.PlugInvoker;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;
import org.fuusio.api.util.Params;

/**
 * {@link PluginFeatureManagerImpl} implements {@link PluginFeatureManager} as a plugin component
 * which is used for managing {@link Feature}s used as plugins.
 */
@Plugin
public class PluginFeatureManagerImpl extends AbstractFeatureManager
        implements PluginFeatureManager {

    @Scope(RobopupuAppScope.class)
    @Provides(PluginFeatureManager.class)
    public PluginFeatureManagerImpl() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public Feature createFeature(final Class<? extends Feature> featureClass, final Params params) {
        Feature feature = D.get(featureClass);

        if (feature == null) {
            feature = super.createFeature(featureClass, params);
        }
        return feature;
    }

    @Override
    public Feature startFeature(final FeatureContainer container, final Feature feature, final Params params) {
        PluginBus.plug(feature);
        return super.startFeature(container, feature, params);
    }
}
