package com.robopupu.feature.main.presenter;

import android.support.annotation.IdRes;

import org.fuusio.api.dependency.D;
import org.fuusio.api.feature.FeatureContainer;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

import com.robopupu.R;
import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.feature.about.AboutFeature;
import com.robopupu.feature.fsm.FsmDemoFeature;
import com.robopupu.feature.main.view.MainView;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.FeatureManager;
import org.fuusio.api.mvp.AbstractPresenter;

@Plugin
public class MainPresenterImpl extends AbstractPresenter<MainView>
        implements MainPresenter {

    @Plug PluginFeatureManager mFeatureManager;
    @Plug MainView mView;

    @Provides(MainPresenter.class)
    public MainPresenterImpl() {
    }

    @Override
    public MainView getViewPlug() {
        return mView;
    }

    @Override
    public void onBackPressed() {
        if (!mFeatureManager.onBackPressed()) {
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

    @Override
    public boolean onNavigationItemSelected(@IdRes final int itemId) {
        boolean selectionHandled = true;

        final FeatureContainer container = mView.getMainFeatureContainer();

        if (itemId == R.id.navigation_about) {
            mFeatureManager.startFeature(container, AboutFeature.class);
        } else if (itemId == R.id.navigation_fsm_demo) {
            mFeatureManager.startFeature(container, FsmDemoFeature.class);
        } else if (itemId == R.id.navigation_settings) {
            //mFeatureManager.startFeature(container, SettingsFeature.class);
        } else if (itemId == R.id.navigation_share) {
            //mFeatureManager.startFeature(container, ShareFeature.class);
        } else {
            selectionHandled = false;
        }
        return selectionHandled;
    }
}
