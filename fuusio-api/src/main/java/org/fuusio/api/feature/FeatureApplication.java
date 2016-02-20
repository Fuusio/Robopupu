package org.fuusio.api.feature;

import org.fuusio.api.app.FuusioApplication;
import org.fuusio.api.dependency.D;

/**
 * {@link FeatureApplication} extends {@link FuusioApplication} to provide an abstract base class
 * for implementing application that are based on {@link Feature}s.
 */
public abstract class FeatureApplication extends FuusioApplication {

    protected final FeatureManager mFeatureManager;

    protected FeatureApplication() {
        mFeatureManager = createFeatureManager();
        FeatureManager.setInstance(mFeatureManager);
    }

    protected FeatureManager createFeatureManager() {
        return D.get(FeatureManager.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(mFeatureManager);
    }

    public FeatureManager getFeatureManager() {
        return mFeatureManager;
    }


}
