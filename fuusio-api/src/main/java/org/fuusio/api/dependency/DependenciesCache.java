package org.fuusio.api.dependency;

import org.fuusio.api.mvp.View;

import java.util.HashMap;

/**
 * {@link DependenciesCache} is used for saving and restoring {@link DependencyScope}s for
 * their {@link DependencyScopeOwner}s, and for saving and accessing {@link DependencyMap}s using
 * {@link String} based keys. {@link View}s have a dedicated method {@link View#getDependenciesKey()}
 * for providing the key.
 */
public class DependenciesCache {

    private final HashMap<String, DependencyMap> mDependencyMaps;
    private final HashMap<String, DependencyScope> mSavedScopes;

    public DependenciesCache() {
        mDependencyMaps = new HashMap<>();
        mSavedScopes = new HashMap<>();
    }

    public void addDependencies(final String key, final DependencyMap dependences) {
        mDependencyMaps.put(key, dependences);
    }

    public DependencyMap addDependencies(final String key) {
        if (mDependencyMaps.containsKey(key)) {
            return mDependencyMaps.get(key);
        } else {
            final DependencyMap dependencies = new DependencyMap();
            mDependencyMaps.put(key, dependencies);
            return dependencies;
        }
    }

    public DependencyMap removeDependencies(final String key) {
        return mDependencyMaps.remove(key);
    }

    public DependencyMap getDependencies(final String key) {
        return mDependencyMaps.get(key);
    }

    public boolean hasDependencies(final View view) {
        return mDependencyMaps.containsKey(view.getDependenciesKey());
    }

    public DependencyMap getDependencies(final View view) {
        return mDependencyMaps.get(view.getDependenciesKey());
    }

    public DependencyMap getDependencies(final View view, final boolean createDependencyMap) {
        final String key = view.getDependenciesKey();

        if (mDependencyMaps.containsKey(key)) {
            return mDependencyMaps.get(key);
        } else if (createDependencyMap) {
            return addDependencies(key);
        }
        return null;
    }

    public DependencyMap removeDependencies(final View view) {
        return mDependencyMaps.remove(view.getDependenciesKey());
    }

    public void saveDependencyScope(final DependencyScopeOwner owner, final DependencyScope scope) {
        mSavedScopes.put(owner.getScopeClass().getName(), scope);
    }

    public DependencyScope getDependencyScope(final DependencyScopeOwner owner) {
        return mSavedScopes.get(owner.getScopeClass().getName());
    }

    public DependencyScope removeDependencyScope(final DependencyScopeOwner owner) {
        return mSavedScopes.remove(owner.getScopeClass().getName());
    }

    public boolean containsDependencyScope(final DependencyScopeOwner owner) {
        return mSavedScopes.containsKey(owner.getScopeClass().getName());
    }

    public void onDependencyScopeOwnerDestroyed(final DependencyScopeOwner owner) {
        final DependencyScope scope = owner.getScope();

        if (scope != null && scope.isDisposable()) {
            removeDependencyScope(owner);
        }
    }
}
