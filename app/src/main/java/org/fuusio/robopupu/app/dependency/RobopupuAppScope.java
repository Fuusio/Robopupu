package org.fuusio.robopupu.app.dependency;

import org.fuusio.robopupu.app.RobopupuApplication;

import org.fuusio.api.app.FuusioAppScope;
import org.fuusio.api.dependency.Scope;

@Scope
public class RobopupuAppScope extends FuusioAppScope {

    public RobopupuAppScope(final RobopupuApplication app) {
        super(app);
    }

    @Override
    protected <T> T getDependency() {
        return super.getDependency();
    }
}
