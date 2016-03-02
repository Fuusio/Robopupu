package com.robopupu.feature.main.presenter;

import android.support.annotation.IdRes;

import org.fuusio.api.plugin.PlugInterface;

import org.fuusio.api.mvp.Presenter;

@PlugInterface
public interface MainPresenter extends Presenter {

    void onBackPressed();

    boolean onNavigationItemSelected(@IdRes int itemId);
}
