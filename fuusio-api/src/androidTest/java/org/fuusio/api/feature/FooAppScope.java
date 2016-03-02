package org.fuusio.api.feature;

import org.fuusio.api.dependency.AppDependencyScope;
import org.fuusio.api.dependency.DependenciesCache;

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
