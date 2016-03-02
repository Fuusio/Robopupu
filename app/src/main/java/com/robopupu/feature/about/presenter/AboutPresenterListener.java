package com.robopupu.feature.about.presenter;

import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface AboutPresenterListener {

    void onShowLicenseInfo();

    void onShowOssLicensesInfo();

    void onOpenSourcesWebPage();
}
