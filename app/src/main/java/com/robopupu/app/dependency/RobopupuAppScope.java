package com.robopupu.app.dependency;

import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.api.feature.PluginFeatureManagerImpl;
import com.robopupu.app.RobopupuApplication;

import org.fuusio.api.app.FuusioAppScope;
import org.fuusio.api.dependency.Scope;

@Scope
public class RobopupuAppScope extends FuusioAppScope {

    public RobopupuAppScope(final RobopupuApplication app) {
        super(app);
    }

    @Override
    protected <T> T getDependency() {

        if (type(PluginFeatureManager.class)) {
            return dependency(new PluginFeatureManagerImpl());
        } else {
            return super.getDependency();
        }
    }
}
