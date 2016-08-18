package com.robopupu.api.dependency;

public class ZooScopeOwner implements DependencyScopeOwner {

    private DependencyScope scope;

    public ZooScopeOwner() {
    }

    @Override
    public DependencyScope getOwnedScope() {
        return scope;
    }

    public void setOwnedScope(ZooScope scope) {
        this.scope = scope;
    }

    public void setOwnedScope(DependencyScope scope) {
        this.scope = scope;
    }

    @Override
    public Class<? extends DependencyScope> getScopeClass() {
        return ZooScope.class;
    }
}
