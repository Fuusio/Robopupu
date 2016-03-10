package com.robopupu.feature.main.presenter;

import com.robopupu.api.mvp.Presenter;
import com.robopupu.api.plugin.PlugInterface;

@PlugInterface
public interface MainPresenter extends Presenter {

    void onBackPressed();

    boolean onNavigationItemSelected(int itemId);
}
