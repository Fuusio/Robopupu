package com.robopupu.feature.main;


import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.plugin.Plugin;

@Plugin
@Scope
public class MainFeatureScope extends DependencyScope {

    public MainFeatureScope() {
        System.out.println();
    }
}
