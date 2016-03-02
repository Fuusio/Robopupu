package org.fuusio.api.dependency;

public class ZooScopeOwner implements DependencyScopeOwner {

    private DependencyScope mScope;

    public ZooScopeOwner() {
    }

    @Override
    public DependencyScope getOwnedScope() {
        return mScope;
    }

    public void setOwnedScope(ZooScope scope) {
        mScope = scope;
    }

    public void setOwnedScope(DependencyScope scope) {
        mScope = scope;
    }

    @Override
    public Class<? extends DependencyScope> getScopeClass() {
        return ZooScope.class;
    }
}
