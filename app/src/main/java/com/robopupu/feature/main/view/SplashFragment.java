package com.robopupu.feature.main.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robopupu.R;
import com.robopupu.feature.main.presenter.SplashPresenter;

import org.fuusio.api.feature.FeatureFragment;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

/**
 * A simple {@link Fragment} subclass.
 */
@Plugin
public class SplashFragment extends FeatureFragment<SplashPresenter> implements SplashView {

    @Plug
    SplashPresenter mPresenter;

    public SplashFragment() {
    }

    @Override
    protected SplashPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

}
