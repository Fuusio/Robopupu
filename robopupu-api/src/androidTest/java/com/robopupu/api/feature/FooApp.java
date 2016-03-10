package com.robopupu.api.feature;

import android.app.Application;

import com.robopupu.api.dependency.DependenciesCache;

public class FooApp extends Application {

    private final DependenciesCache mDependenciesCache;

    public FooApp() {
        mDependenciesCache = new DependenciesCache();
    }

    public Object getDependenciesCache() {
        return mDependenciesCache;
    }
}
