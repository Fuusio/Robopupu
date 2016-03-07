/*
 * Copyright (C) 2016 Marko Salmela, http://fuusio.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    private Binding mVersionTextBinding;

    @Provides(AboutView.class)
    public AboutFragment() {
        super(R.string.ft_about_title);
    }

    @Override
    public AboutPresenter getPresenter() {
        return mPresenter;
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
                mPresenter.onViewOssLicensesClick();
            }
        };

        new ClickBinding(this, R.id.text_view_label_license, R.id.text_view_license, R.id.image_view_arrow_license) {
            @Override protected void clicked() {
                mPresenter.onViewLicenseClick();
            }
        };

        new ClickBinding(this, R.id.text_view_label_sources, R.id.text_view_sources, R.id.image_view_arrow_sources) {
            @Override protected void clicked() {
                mPresenter.onViewSourcesClick();
            }
        };

        mVersionTextBinding = bind(R.id.text_view_version);
    }

}
