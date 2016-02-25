package com.robopupu.feature.fsm;

import com.robopupu.app.RobopupuAppScope;
import com.robopupu.feature.fsm.presenter.FsmDemoPresenter;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.feature.AbstractFeature;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class FsmDemoFeatureImpl extends AbstractFeature implements FsmDemoFeature {

    @Scope(RobopupuAppScope.class)
    @Provides(FsmDemoFeature.class)
    public FsmDemoFeatureImpl() {
        super(FsmDemoScope.class);
    }

    @Override
    protected void onStart() {
        showView(FsmDemoPresenter.class);
    }
}
