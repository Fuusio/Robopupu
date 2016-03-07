package com.robopupu.feature.about.presenter;

import org.fuusio.api.feature.FeaturePresenter;
import org.fuusio.api.mvp.OnClick;
import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface AboutPresenter extends FeaturePresenter {

    @OnClick
    void onViewLicenseClick();

    @OnClick
    void onViewOssLicensesClick();

    @OnClick
    void onViewSourcesClick();
}
