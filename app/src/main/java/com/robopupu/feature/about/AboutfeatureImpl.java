package com.robopupu.feature.about;

import com.robopupu.app.RobopupuAppScope;
import com.robopupu.feature.about.presenter.AboutPresenter;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.feature.AbstractFeature;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;

@Plugin
public class AboutFeatureImpl extends AbstractFeature implements AboutFeature {

    @Scope(RobopupuAppScope.class)
    @Provides(AboutFeature.class)
    public AboutFeatureImpl() {
        super(AboutFeatureScope.class);
    }

    @Override
    protected void onStart() {
        showView(AboutPresenter.class);
    }
}
