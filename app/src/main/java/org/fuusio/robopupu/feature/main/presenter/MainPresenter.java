package org.fuusio.robopupu.feature.main.presenter;

import org.fuusio.api.plugin.PlugInterface;
import org.fuusio.robopupu.feature.main.view.MainView;

import org.fuusio.api.mvp.Presenter;

@PlugInterface
public interface MainPresenter extends Presenter {

    void onBackPressed();
    void onExitAppSelected();
}
