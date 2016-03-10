package com.robopupu.api.dependency;

public class PubScope extends DependencyScope {

    @Override
    protected <T> T getDependency() {
        if (type(Beer.class)) {
            return dependency(new Beer());
        }
        return null;
    }
}
