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
import android.net.Uri;

import com.robopupu.R;
import com.robopupu.component.AppManager;
import com.robopupu.feature.feedback.FeedbackFeature;
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

    private boolean mFeedbackSend;

    @Plug AppManager mAppManager;
    @Plug FeedbackFeature mFeature;
    @Plug FeedbackView mView;

    @Provides(FeedbackPresenter.class)
    public FeedbackPresenterImpl() {
        mFeedbackSend = false;
    }

    @Override
    public FeedbackView getViewPlug() {
        return mView;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus);
        plug(FeedbackView.class);
    }

    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);
    }

    @Override
    public void onViewResume(final View view) {
        super.onViewResume(view);

        if (mFeedbackSend) {
            mFeature.showThxView();
            mFeedbackSend = false;
        }
    }

    @Override
    public void onSendClicked(final String feedback) {
        final StringBuilder uri = new StringBuilder("mailto:");
        uri.append(Uri.encode("robopupu@gmail.com"));
        uri.append("?subject=");
        uri.append(Uri.encode("ROBOPUPU feedback"));
        uri.append("&body=");
        uri.append(Uri.encode(feedback));

        final Intent sendIntent = new Intent(android.content.Intent.ACTION_SENDTO);
        sendIntent.setType("message/rfc822");
        sendIntent.setData(Uri.parse(uri.toString()));

        try {
            mAppManager.startActivity(Intent.createChooser(sendIntent, mAppManager.getString(R.string.ft_feedback_prompt_send_email_using)));
        } catch (ActivityNotFoundException e) {
            mView.showMessage(mAppManager.getString(R.string.ft_feedback_error_no_email_client));
        }
        mFeedbackSend = true;
    }
}
