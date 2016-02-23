package com.robopupu.feature.about.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robopupu.R;
import com.robopupu.feature.about.presenter.AboutPresenter;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.FeatureFragment;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class AboutFragment extends FeatureFragment<AboutPresenter> implements AboutView {

    @Plug AboutPresenter mPresenter;

    private TextView mLicenseTextView;
    private TextView mVersionTextView;

    @Provides(AboutView.class)
    public AboutFragment() {
    }

    @Override
    protected AboutPresenter getPresenter() {
        return mPresenter;
    }


    @Override
    public void setLicenseText(final String text) {
        // mLicenseTextView.setText(text);
    }

    @Override
    public void setVersionText(final String text) {
        mVersionTextView.setText(text);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    protected void createBindings() {
        mVersionTextView = getView(R.id.text_view_label_license);
    }
}
