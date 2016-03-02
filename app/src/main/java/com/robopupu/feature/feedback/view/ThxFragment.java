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
package com.robopupu.feature.feedback.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robopupu.R;
import com.robopupu.feature.feedback.presenter.ThxPresenter;


import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.FeatureFragment;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

/**
 * A simple {@link Fragment} subclass.
 */
@Plugin
public class ThxFragment extends FeatureFragment<ThxPresenter> implements ThxView {

    @Plug
    ThxPresenter mPresenter;

    @Provides(ThxView.class)
    public ThxFragment() {
    }

    @Override
    protected ThxPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_thx, container, false);
    }

}
