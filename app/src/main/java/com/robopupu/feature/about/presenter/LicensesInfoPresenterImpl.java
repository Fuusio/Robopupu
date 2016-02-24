package com.robopupu.feature.about.presenter;

import android.webkit.WebView;

import com.robopupu.R;
import com.robopupu.component.AppManager;
import com.robopupu.feature.about.view.LicensesInfoView;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.AbstractFeaturePresenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;

@Plugin
public class LicensesInfoPresenterImpl extends AbstractFeaturePresenter<LicensesInfoView>
        implements LicensesInfoPresenter {

    private String mLicensesFileUrl;

    @Plug AppManager mAppManager;
    @Plug LicensesInfoView mView;

    @Provides(LicensesInfoPresenter.class)
    public LicensesInfoPresenterImpl() {
    }

    @Override
    public LicensesInfoView getViewPlug() {
        return mView;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus); // TODO : Force call super
        plug(LicensesInfoView.class);
    }

    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);

        mLicensesFileUrl = mAppManager.getString(R.string.oss_licenses_info_file);
        mView.loadUrl(mLicensesFileUrl);
    }

    @Override
    public boolean shouldLoadUrl(final WebView webView, final String url) {
        return mLicensesFileUrl.contentEquals(url);
    }

    @Override
    public void onUrlLoadingFailed(final WebView webView, final String url, final int errorCode) {
        // Do nothing
    }

    @Override
    public void onUrlLoadingSucceeded(final WebView webView, final String url) {
        // Do nothing
    }

    @Override
    public void onUrlReloadingSucceeded(final WebView webView, final String url) {
        // Do nothing
    }

    @Override
    public void onOkButtonClicked() {
        finish();
    }
}
