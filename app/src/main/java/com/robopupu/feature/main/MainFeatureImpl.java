package com.robopupu.feature.main;

import com.robopupu.feature.main.presenter.MainPresenter;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.AbstractFeature;
import org.fuusio.api.feature.FeatureContainer;
import org.fuusio.api.feature.FeatureScope;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;
import org.fuusio.api.util.Params;

@Plugin
public class MainFeatureImpl extends AbstractFeature implements MainFeature {

    @Plug MainPresenter mMainPresenter;

    @Provides(MainFeature.class)
    public MainFeatureImpl() {
        super(MainFeatureScope.class);
    }


}
