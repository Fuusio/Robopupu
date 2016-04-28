package com.robopupu.feature.hackernews;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;
import com.robopupu.api.feature.AbstractFeature;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.app.RobopupuAppScope;

@Plugin
public class HackerNewsFeatureImpl extends AbstractFeature implements HackerNewsFeature {

    @Scope(RobopupuAppScope.class)
    @Provides(HackerNewsFeature.class)
    public HackerNewsFeatureImpl() {
        super(HackerNewsFeatureScope.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //showView(JokesPresenter.class, false);
        //plug(JokesInteractor.class);
    }
}
