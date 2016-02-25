package com.robopupu.feature.main;

import com.robopupu.app.RobopupuAppScope;
import com.robopupu.feature.main.presenter.MainPresenter;
import com.robopupu.feature.main.presenter.SplashPresenter;
import com.robopupu.feature.main.view.MainView;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.feature.AbstractFeature;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class MainFeatureImpl extends AbstractFeature implements MainFeature {

    @Plug MainView mMainView;

    @Scope(RobopupuAppScope.class)
    @Provides(MainFeature.class)
    public MainFeatureImpl() {
        super(MainFeatureScope.class, true);
    }

    @Override
    public void onPresenterStarted(final Presenter presenter) {

        if (presenter instanceof MainPresenter) {
            showView(mMainView.getMainFeatureContainer(), SplashPresenter.class); // TODO
        }
    }
}
