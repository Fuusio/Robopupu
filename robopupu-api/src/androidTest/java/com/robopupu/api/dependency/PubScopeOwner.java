package com.robopupu.api.dependency;

public class PubScopeOwner implements DependencyScopeOwner {

    private DependencyScope mScope;

    public PubScopeOwner() {
    }

    @Override
    public DependencyScope getOwnedScope() {
        return mScope;
    }

    public void setOwnedScope(DependencyScope scope) {
        mScope = scope;
    }

    @Override
    public Class<? extends DependencyScope> getScopeClass() {
        return PubScope.class;
    }
}
