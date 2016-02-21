package com.robopupu.feature.main.presenter;

import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import com.robopupu.feature.main.view.MainView;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.FeatureManager;
import org.fuusio.api.mvp.AbstractPresenter;

@Plugin
public class MainPresenterImpl extends AbstractPresenter<MainView>
        implements MainPresenter {

    @Plug MainView mView;

    @Provides(MainPresenter.class)
    public MainPresenterImpl() {
    }

    @Override
    public MainView getView() {
        return mView;
    }

    @Override
    public void onBackPressed() {

        final FeatureManager manager = get(FeatureManager.class);

        if (!manager.onBackPressed()) {
            mView.showExitConfirmDialog();
        }
    }

    @Override
    public void onExitAppSelected() {
        doExitApp();
    }

    private void doExitApp() {
        mView.finish();
    }
}
