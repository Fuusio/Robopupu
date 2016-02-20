package org.fuusio.robopupu.feature.main;


import org.fuusio.api.dependency.Scope;
import org.fuusio.api.feature.FeatureScope;
import org.fuusio.api.plugin.Plugin;

@Plugin
@Scope
public class MainFeatureScope extends FeatureScope<MainFeature> {

    public MainFeatureScope(final MainFeature feature) {
        super(feature);
    }
}
