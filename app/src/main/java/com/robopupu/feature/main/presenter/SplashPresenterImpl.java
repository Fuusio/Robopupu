package com.robopupu.feature.main.presenter;

import com.robopupu.feature.main.view.SplashView;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.AbstractFeaturePresenter;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

/**
 * {@link SplashPresenterImpl} ...
 */
@Plugin
public class SplashPresenterImpl extends AbstractFeaturePresenter<SplashView>
        implements SplashPresenter {

    @Plug
    SplashView mView;

    @Provides(SplashPresenter.class)
    public SplashPresenterImpl() {
    }

    @Override
    protected SplashView getView() {
        return mView;
    }
}
