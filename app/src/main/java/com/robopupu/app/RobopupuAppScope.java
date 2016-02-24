package com.robopupu.app;

import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.api.feature.PluginFeatureManagerImpl;
import com.robopupu.app.RobopupuApplication;

import org.fuusio.api.app.FuusioAppScope;
import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;

@Scope
public class RobopupuAppScope extends FuusioAppScope<RobopupuApplication> {

    public RobopupuAppScope(final RobopupuApplication app) {
        super(app);
    }

    @Provides
    public RobopupuApplication getRobopupuApplication() {
        return getApplication();
    }
}
