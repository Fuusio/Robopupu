package com.robopupu.feature.about.presenter;

import com.robopupu.api.feature.FeaturePresenter;
import com.robopupu.api.mvp.OnClick;
import com.robopupu.api.plugin.PlugInterface;

@PlugInterface
public interface AboutPresenter extends FeaturePresenter {

    @OnClick
    void onViewLicenseClick();

    @OnClick
    void onViewOssLicensesClick();

    @OnClick
    void onViewSourcesClick();
}
