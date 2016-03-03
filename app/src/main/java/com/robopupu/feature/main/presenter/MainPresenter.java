package com.robopupu.feature.main.presenter;

import android.support.annotation.IdRes;

import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface MainPresenter extends Presenter {

    void onBackPressed();

    boolean onNavigationItemSelected(@IdRes int itemId);
}
