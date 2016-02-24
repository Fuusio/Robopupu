package com.robopupu.feature.about;

import com.robopupu.app.RobopupuAppScope;
import com.robopupu.feature.about.presenter.AboutPresenter;
import com.robopupu.feature.about.presenter.LicensesInfoPresenter;
import com.robopupu.feature.about.view.LicensesInfoView;

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

    @Override
    public void showLicensesInfo() {
        showView(LicensesInfoPresenter.class);
    }

    @Override
    public void onPresenterFinished(final Presenter presenter) {

        if (presenter instanceof LicensesInfoPresenter) {
            unplug(presenter); // TODO How to unplug the View as well
            goBack();
        }

    }
}
