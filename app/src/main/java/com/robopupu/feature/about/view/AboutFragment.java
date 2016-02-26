package com.robopupu.feature.about.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robopupu.R;
import com.robopupu.app.view.CoordinatorLayoutFragment;
import com.robopupu.feature.about.presenter.AboutPresenter;

import org.fuusio.api.binding.Binding;
import org.fuusio.api.binding.ClickBinding;
import org.fuusio.api.dependency.Provides;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class AboutFragment extends CoordinatorLayoutFragment<AboutPresenter> implements AboutView {

    @Plug AboutPresenter mPresenter;

    private Binding mLicenseTextBinding;
    private Binding mVersionTextBinding;

    @Provides(AboutView.class)
    public AboutFragment() {
        super(R.string.ft_about_title_robopupu);
    }

    @Override
    protected AboutPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setLicenseText(final String text) {
        mLicenseTextBinding.setText(text);
    }

    @Override
    public void setVersionText(final String text) {
        mVersionTextBinding.setText(text);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    protected void createBindings() {
        super.createBindings();

        new ClickBinding(this, R.id.text_view_label_oss_licenses, R.id.text_view_oss_licenses, R.id.image_view_arrow_oss_licenses) {
            @Override protected void clicked() {
                mPresenter.onViewOssLicensesTextClicked();
            }
        };

        new ClickBinding(this, R.id.text_view_label_license, R.id.text_view_license, R.id.image_view_arrow_license) {
            @Override protected void clicked() {
                mPresenter.onViewLicenseTextClicked();
            }
        };

        mLicenseTextBinding = bind(R.id.text_view_license);
        mVersionTextBinding = bind(R.id.text_view_version);
    }

}
