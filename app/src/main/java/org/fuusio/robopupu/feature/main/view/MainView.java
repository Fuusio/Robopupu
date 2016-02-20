package org.fuusio.robopupu.feature.main.view;

import org.fuusio.api.plugin.PlugInterface;
import org.fuusio.robopupu.feature.main.presenter.MainPresenter;

import org.fuusio.api.mvp.View;

@PlugInterface
public interface MainView extends View {

    /**
     * Invoked to show an Exit Dialog.
     */
    void showExitConfirmDialog();

    /**
     * Finish this {@link View}.
     */
    void finish();
}
