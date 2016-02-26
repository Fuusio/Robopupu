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
