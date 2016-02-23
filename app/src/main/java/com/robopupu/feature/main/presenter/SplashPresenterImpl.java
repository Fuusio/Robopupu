package com.robopupu.feature.main.presenter;

import com.robopupu.feature.main.view.SplashView;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.AbstractFeaturePresenter;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;

/**
 * {@link SplashPresenterImpl} ...
 */
@Plugin
public class SplashPresenterImpl extends AbstractFeaturePresenter<SplashView>
        implements SplashPresenter {

    @Plug SplashView mView;

    @Provides(SplashPresenter.class)
    public SplashPresenterImpl() {
    }

    @Override
    public SplashView getViewPlug() {
        return mView;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        plug(SplashView.class);
    }


}
