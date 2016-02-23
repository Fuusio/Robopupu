package com.robopupu.feature.main;

import com.robopupu.app.RobopupuAppScope;
import com.robopupu.feature.main.presenter.MainPresenter;
import com.robopupu.feature.main.presenter.SplashPresenter;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.feature.AbstractFeature;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class MainFeatureImpl extends AbstractFeature implements MainFeature {

    @Scope(RobopupuAppScope.class)
    @Provides(MainFeature.class)
    public MainFeatureImpl() {
        super(MainFeatureScope.class);
    }

    @Override
    public void onPresenterStarted(final Presenter presenter) {

        if (presenter instanceof MainPresenter) {
            showView(SplashPresenter.class);
        }
    }
}
