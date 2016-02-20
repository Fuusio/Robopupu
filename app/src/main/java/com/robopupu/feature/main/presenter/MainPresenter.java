package com.robopupu.feature.main.presenter;

import org.fuusio.api.plugin.PlugInterface;

import org.fuusio.api.mvp.Presenter;

@PlugInterface
public interface MainPresenter extends Presenter {

    void onBackPressed();
    void onExitAppSelected();
}
