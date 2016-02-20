package org.fuusio.robopupu.feature.main;

import org.fuusio.api.feature.AbstractFeature;
import org.fuusio.api.feature.FeatureContainer;
import org.fuusio.api.feature.FeatureScope;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.util.Params;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

@Plugin
public class MainFeatureImpl extends AbstractFeature implements MainFeature {

    public MainFeatureImpl(final FeatureContainer container, final Params params) {
        super(container, params);
    }

    @Override
    protected FeatureScope createDependencyScope() {
        return new MainFeatureScope(this);
    }
}
