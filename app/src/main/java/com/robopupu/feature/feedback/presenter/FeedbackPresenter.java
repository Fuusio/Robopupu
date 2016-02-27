package com.robopupu.feature.feedback.presenter;

import org.fuusio.api.feature.FeaturePresenter;
import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface FeedbackPresenter extends FeaturePresenter {

    void onSendClicked(String feedbackText);
}
