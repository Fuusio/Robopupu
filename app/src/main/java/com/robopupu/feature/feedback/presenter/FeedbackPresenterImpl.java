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
package com.robopupu.feature.feedback.presenter;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import com.robopupu.R;
import com.robopupu.component.AppManager;
import com.robopupu.feature.feedback.view.FeedbackView;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.AbstractFeaturePresenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;

@Plugin
public class FeedbackPresenterImpl extends AbstractFeaturePresenter<FeedbackView>
        implements FeedbackPresenter {

    @Plug AppManager mAppManager;
    @Plug FeedbackView mView;

    @Provides(FeedbackPresenter.class)
    public FeedbackPresenterImpl() {
    }

    @Override
    public FeedbackView getViewPlug() {
        return mView;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        plug(FeedbackView.class);
    }

    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);
    }

    @Override
    public void onSendClicked(final String feedback) {
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{  "robopupu@gmail.com"});
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ROBOPUPU feedback");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, feedback);

        try {
            mAppManager.startActivity(Intent.createChooser(intent, mAppManager.getString(R.string.ft_feedback_prompt_send_email_using)));
        } catch (ActivityNotFoundException e) {
            mView.showMessage(mAppManager.getString(R.string.ft_feedback_error_no_email_client));
        }
    }
}
