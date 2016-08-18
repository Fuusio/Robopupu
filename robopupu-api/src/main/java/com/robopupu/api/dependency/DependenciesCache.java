package com.robopupu.api.dependency;

import com.robopupu.api.mvp.View;

import java.util.HashMap;

/**
 * {@link DependenciesCache} is used for saving and restoring {@link DependencyScope}s for
 * their {@link DependencyScopeOwner}s, and for saving and accessing {@link DependencyMap}s using
 * {@link String} based keys. {@link View}s have a dedicated method {@link View#getViewTag()}
 * for providing the key.
 */
public class DependenciesCache {

    private final HashMap<String, DependencyMap> dependencyMaps;
    private final HashMap<String, DependencyScope> savedScopes;

    public DependenciesCache() {
        dependencyMaps = new HashMap<>();
        savedScopes = new HashMap<>();
    }

    public void addDependencies(final String key, final DependencyMap dependences) {
        dependencyMaps.put(key, dependences);
    }

    public DependencyMap addDependencies(final String key) {
        if (dependencyMaps.containsKey(key)) {
            return dependencyMaps.get(key);
        } else {
            final DependencyMap dependencies = new DependencyMap();
            dependencyMaps.put(key, dependencies);
            return dependencies;
        }
    }

    public DependencyMap removeDependencies(final String key) {
        return dependencyMaps.remove(key);
    }

    public DependencyMap getDependencies(final String key) {
        return dependencyMaps.get(key);
    }

    public boolean hasDependencies(final View view) {
        return dependencyMaps.containsKey(view.getViewTag());
    }

    public DependencyMap getDependencies(final View view) {
        return dependencyMaps.get(view.getViewTag());
    }

    public DependencyMap getDependencies(final View view, final boolean createDependencyMap) {
        final String key = view.getViewTag();

        if (dependencyMaps.containsKey(key)) {
            return dependencyMaps.get(key);
        } else if (createDependencyMap) {
            return addDependencies(key);
        }
        return null;
    }

    public DependencyMap removeDependencies(final View view) {
        return dependencyMaps.remove(view.getViewTag());
    }

    public void saveDependencyScope(final DependencyScopeOwner owner, final DependencyScope scope) {
        savedScopes.put(owner.getScopeClass().getName(), scope);
    }

    public DependencyScope getDependencyScope(final DependencyScopeOwner owner) {
        return savedScopes.get(owner.getScopeClass().getName());
    }

    public DependencyScope removeDependencyScope(final DependencyScopeOwner owner) {
        return savedScopes.remove(owner.getScopeClass().getName());
    }

    public boolean containsDependencyScope(final DependencyScopeOwner owner) {
        return savedScopes.containsKey(owner.getScopeClass().getName());
    }

    public void onDependencyScopeOwnerDestroyed(final DependencyScopeOwner owner) {
        final DependencyScope scope = owner.getOwnedScope();

        if (scope != null && scope.isDisposable()) {
            removeDependencyScope(owner);
        }
        D.disposeScope(owner);
    }
}
