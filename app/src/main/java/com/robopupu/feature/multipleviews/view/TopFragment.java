package com.robopupu.feature.multipleviews.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robopupu.R;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.FeatureCompatFragment;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.feature.multipleviews.presenter.TopPresenter;

@Plugin
@Provides(TopView.class)
public class TopFragment extends FeatureCompatFragment<TopPresenter> implements TopView {

    @Plug
    TopPresenter presenter;

    @Override
    public TopPresenter getPresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_top, container, false);
    }
}
