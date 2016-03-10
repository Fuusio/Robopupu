package com.robopupu.feature.about.presenter;

import com.robopupu.api.plugin.PlugInterface;

@PlugInterface
public interface AboutPresenterListener {

    void onShowLicenseInfo();

    void onShowOssLicensesInfo();

    void onOpenSourcesWebPage();
}
