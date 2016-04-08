package com.robopupu.feature.jokes;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;
import com.robopupu.api.feature.AbstractFeature;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.app.RobopupuAppScope;
import com.robopupu.feature.jokes.component.JokesInteractor;
import com.robopupu.feature.jokes.presenter.JokesPresenter;

@Plugin
public class JokesFeatureImpl extends AbstractFeature implements JokesFeature {

    @Scope(RobopupuAppScope.class)
    @Provides(JokesFeature.class)
    public JokesFeatureImpl() {
        super(JokesFeatureScope.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showView(JokesPresenter.class, false);
        plug(JokesInteractor.class);
    }
}
