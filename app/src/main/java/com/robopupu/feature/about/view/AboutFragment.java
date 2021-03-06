/*
 * Copyright (C) 2016 Marko Salmela, http://robopupu.com
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
import com.robopupu.api.binding.Binding;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.app.view.CoordinatorLayoutFragment;
import com.robopupu.feature.about.presenter.AboutPresenter;

@Plugin
public class AboutFragment extends CoordinatorLayoutFragment<AboutPresenter> implements AboutView {

    @Plug AboutPresenter presenter;

    private Binding versionTextBinding;

    @Provides(AboutView.class)
    public AboutFragment() {
        super(R.string.ft_about_title);
    }

    @Override
    public AboutPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void setVersionText(final String text) {
        versionTextBinding.setText(text);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    protected void onCreateBindings() {
        super.onCreateBindings();
        versionTextBinding = bind(R.id.text_view_version);
    }
}