package com.robopupu.feature.main.view;

import org.fuusio.api.feature.FeatureContainer;
import org.fuusio.api.plugin.PlugInterface;

import org.fuusio.api.mvp.View;

@PlugInterface
public interface MainView extends View {

    FeatureContainer getMainFeatureContainer();

    /**
     * Invoked to show an Exit Dialog.
     */
    void showExitConfirmDialog();

    /**
     * Finish this {@link View}.
     */
    void finish();
}
