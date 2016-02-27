package com.robopupu.feature.feedback;

import com.robopupu.app.RobopupuAppScope;
import com.robopupu.feature.feedback.presenter.FeedbackPresenter;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.feature.AbstractFeature;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class FeedbackFeatureImpl extends AbstractFeature implements FeedbackFeature {

    @Scope(RobopupuAppScope.class)
    @Provides(FeedbackFeature.class)
    public FeedbackFeatureImpl() {
        super(FeedbackFeatureScope.class);
    }

    @Override
    protected void onStart() {
        showView(FeedbackPresenter.class);
    }
}
