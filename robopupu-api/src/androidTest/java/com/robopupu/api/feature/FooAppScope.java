package com.robopupu.api.feature;

import com.robopupu.api.dependency.AppDependencyScope;
import com.robopupu.api.dependency.DependenciesCache;

public class FooAppScope extends AppDependencyScope<FooApp> {

    public FooAppScope(FooApp app) {
        super(app);
    }

    @Override
    protected <T> T getDependency() {
        if (type(DependenciesCache.class)) {
            return dependency(getApplication().getDependenciesCache());
        }
        return super.getDependency();
    }
}
