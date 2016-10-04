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
package com.robopupu.feature.feedback.presenter;

import com.robopupu.R;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.AbstractFeaturePresenter;
import com.robopupu.api.mvp.View;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.component.AppManager;
import com.robopupu.component.PlatformManager;
import com.robopupu.feature.feedback.FeedbackFeature;
import com.robopupu.feature.feedback.view.FeedbackView;

@Plugin
public class FeedbackPresenterImpl extends AbstractFeaturePresenter<FeedbackView>
        implements FeedbackPresenter {

    private boolean isFeedbackSend;

    @Plug AppManager appManager;
    @Plug FeedbackFeature feature;
    @Plug PlatformManager platformManager;
    @Plug FeedbackView view;

    @Provides(FeedbackPresenter.class)
    public FeedbackPresenterImpl() {
        isFeedbackSend = false;
    }

    @Override
    public FeedbackView getViewPlug() {
        return view;
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus);
        plug(FeedbackView.class);
    }

    @Override
    public void onViewResume(final View view) {
        super.onViewResume(view);

        if (isFeedbackSend) {
            feature.showThxView();
            isFeedbackSend = false;
        }
    }

    @Override
    public void onSendClicked(final String feedback) {
        final String address = appManager.getString(R.string.robopupu_email_address);
        final String subject = appManager.getString(R.string.ft_feedback_text_subject);
        final String chooserTitle = appManager.getString(R.string.ft_feedback_prompt_send_email_using);

        if (!platformManager.sendEmail(address, subject, feedback, chooserTitle)) {
            view.showMessage(appManager.getString(R.string.ft_feedback_error_no_email_client));
        }
        isFeedbackSend = true;
    }
}
