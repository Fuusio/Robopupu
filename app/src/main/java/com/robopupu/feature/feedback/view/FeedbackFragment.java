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
package com.robopupu.feature.feedback.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robopupu.R;
import com.robopupu.app.view.CoordinatorLayoutFragment;
import com.robopupu.feature.feedback.presenter.FeedbackPresenter;

import com.robopupu.api.binding.Binding;
import com.robopupu.api.binding.ClickBinding;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;

@Plugin
public class FeedbackFragment extends CoordinatorLayoutFragment<FeedbackPresenter>
        implements FeedbackView {

    private Binding mFeedbackTextBinding;

    @Plug FeedbackPresenter mPresenter;

    @Provides(FeedbackView.class)
    public FeedbackFragment() {
        super(R.string.ft_feedback_title);
    }

    @Override
    public FeedbackPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFeedbackTextBinding.requestFocus();
    }

    @Override
    protected void onCreateBindings() {
        super.onCreateBindings();

        mFeedbackTextBinding = new Binding(this, R.id.edit_text_feedback);
    }

    @Override
    protected void setupFabAction(FloatingActionButton fab) {
        new ClickBinding(this, fab) {
            @Override
            protected void clicked() {
                mPresenter.onSendClicked(mFeedbackTextBinding.getText());
            }
        };
    }

    @Override
    public void clearFeedbackText() {
        mFeedbackTextBinding.setText("");
    }
}
