package org.fuusio.api.feature;

import android.app.Application;

import org.fuusio.api.dependency.DependenciesCache;

public class FooApp extends Application {

    private final DependenciesCache mDependenciesCache;

    public FooApp() {
        mDependenciesCache = new DependenciesCache();
    }

    public Object getDependenciesCache() {
        return mDependenciesCache;
    }
}
