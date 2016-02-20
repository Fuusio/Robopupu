package com.robopupu.feature.main;

import org.fuusio.api.feature.AbstractFeature;
import org.fuusio.api.feature.FeatureContainer;
import org.fuusio.api.feature.FeatureScope;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;
import org.fuusio.api.util.Params;

@Plugin
public class MainFeatureImpl extends AbstractFeature implements MainFeature {

    public MainFeatureImpl(final FeatureContainer container, final Params params) {
        super(container, params);
    }

    @Override
    protected FeatureScope createDependencyScope() {
        return new MainFeatureScope(this);
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        System.out.println();
    }
}
