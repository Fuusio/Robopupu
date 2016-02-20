package org.fuusio.robopupu.app;

import org.fuusio.robopupu.app.dependency.RobopupuAppScope;

import org.fuusio.api.dependency.AppDependencyScope;
import org.fuusio.api.feature.FeatureApplication;

public class RobopupuApplication extends FeatureApplication {

    // Google Analytics Property ID
    public final static int PROPERTY_ID = 0; // TODO

    public RobopupuApplication() {
        super();
    }

    @Override
    protected AppDependencyScope createAppScope() {
        return new RobopupuAppScope(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RobopupuAppError.setContext(getApplicationContext());
    }

    /**
     * Gets the Google Analytics Property ID.
     * @return The property ID as an {@code int} value.
     */
    public int getAnalyticsPropertyId() {
        return PROPERTY_ID;
    }

    @Override
    protected void configureApplication() {
        super.configureApplication();
    }
}
