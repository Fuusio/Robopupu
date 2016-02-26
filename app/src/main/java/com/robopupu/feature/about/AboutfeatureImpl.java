package com.robopupu.feature.about;

import com.robopupu.R;
import com.robopupu.app.RobopupuAppScope;
import com.robopupu.component.AppManager;
import com.robopupu.feature.about.presenter.AboutPresenter;
import com.robopupu.feature.about.presenter.LicensesInfoPresenter;
import com.robopupu.feature.about.view.LicensesInfoView;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.feature.AbstractFeature;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;
import org.fuusio.api.util.Params;

@Plugin
public class AboutFeatureImpl extends AbstractFeature implements AboutFeature {

    @Plug AppManager mAppManager;

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
    public void showLicenseInfo() {
        final Params params = new Params();
        params.put(LicensesInfoPresenter.KEY_PARAM_LICENSE_URL, mAppManager.getString(R.string.robopupu_license_file));
        showView(LicensesInfoPresenter.class, params);
    }

    @Override
    public void showOssLicensesInfo() {
        final Params params = new Params();
        params.put(LicensesInfoPresenter.KEY_PARAM_LICENSE_URL, mAppManager.getString(R.string.oss_licenses_file));
        showView(LicensesInfoPresenter.class, params);
    }

    @Override
    public void onPresenterFinished(final Presenter presenter) {

        if (presenter instanceof LicensesInfoPresenter) {
            goBack();
        }
    }
}
