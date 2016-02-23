package com.robopupu.api.feature;

import org.fuusio.api.dependency.D;
import org.fuusio.api.feature.AbstractFeatureManager;
import org.fuusio.api.feature.Feature;
import org.fuusio.api.plugin.PlugInvoker;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;
import org.fuusio.api.util.Params;

/**
 * {@link PluginFeatureManagerImpl} implements {@link PluginFeatureManager} as a plugin component.
 */
@Plugin
public class PluginFeatureManagerImpl extends AbstractFeatureManager
        implements PluginFeatureManager {

    @SuppressWarnings("unchecked")
    @Override
    public Feature createFeature(final Class<? extends Feature> featureClass, final Params params) {
        Feature feature = null;

        feature = D.get(featureClass);

        if (feature == null) {
            feature = super.createFeature(featureClass, params);
        }
        return feature;
    }


    @Override
    public Feature startFeature(final Feature feature, final Params params) {
        if (feature instanceof PlugInvoker) {
            PluginBus.plug(((PlugInvoker)feature).get(0));
        } else {
            PluginBus.plug(feature);
        }
        return super.startFeature(feature, params);
    }
}
