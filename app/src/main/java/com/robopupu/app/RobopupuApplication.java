package com.robopupu.app;

import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.app.error.RobopupuAppError;
import com.robopupu.component.AppManager;

import org.fuusio.api.app.FuusioApplication;
import org.fuusio.api.dependency.AppDependencyScope;
import org.fuusio.api.plugin.PluginBus;

public class RobopupuApplication extends FuusioApplication {

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

        PluginBus.plug(AppManager.class);
        final PluginFeatureManager featureManager = PluginBus.plug(PluginFeatureManager.class);
        registerActivityLifecycleCallbacks(featureManager.getActivityLifecycleCallback());
    }
}
