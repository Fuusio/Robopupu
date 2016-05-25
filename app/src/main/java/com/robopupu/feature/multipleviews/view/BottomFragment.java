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
import com.robopupu.feature.multipleviews.presenter.BottomPresenter;

@Plugin
@Provides(BottomView.class)
public class BottomFragment extends FeatureCompatFragment<BottomPresenter> implements BottomView {

    @Plug
    BottomPresenter mPresenter;

    @Override
    public BottomPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_bottom, container, false);
    }
}
