package com.robopupu.api.dependency;

public class PubScopeOwner implements DependencyScopeOwner {

    private DependencyScope scope;

    public PubScopeOwner() {
    }

    @Override
    public DependencyScope getOwnedScope() {
        return scope;
    }

    public void setOwnedScope(DependencyScope scope) {
        this.scope = scope;
    }

    @Override
    public Class<? extends DependencyScope> getScopeClass() {
        return PubScope.class;
    }
}
