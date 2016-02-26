package com.robopupu.feature.about;

import org.fuusio.api.feature.Feature;
import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface AboutFeature extends Feature {

    void showLicenseInfo();
    void showOssLicensesInfo();
}
