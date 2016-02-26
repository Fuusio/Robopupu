package com.robopupu.feature.about.presenter;

import com.robopupu.R;
import com.robopupu.component.AppManager;
import com.robopupu.feature.about.AboutFeature;
import com.robopupu.feature.about.view.AboutView;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.AbstractFeaturePresenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;

@Plugin
public class AboutPresenterImpl extends AbstractFeaturePresenter<AboutView>
        implements AboutPresenter {

    @Plug AboutFeature mFeature;
    @Plug AppManager mAppManager;
    @Plug AboutView mView;

    @Provides(AboutPresenter.class)
    public AboutPresenterImpl() {
    }

    @Override
    public AboutView getViewPlug() {
        return mView;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        plug(AboutView.class);
    }

    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);

        mView.setLicenseText(mAppManager.getString(R.string.ft_about_text_license));
        mView.setVersionText(mAppManager.getAppVersionName());
    }

    @Override
    public void onViewLicenseTextClicked() {
        mFeature.showLicenseInfo();
    }

    @Override
    public void onViewOssLicensesTextClicked() {
        mFeature.showOssLicensesInfo();
    }
}
