package com.robopupu.api.dependency;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * {@link DependencyQuery} is an object used for query one or more dependencies from
 * {@link DependencyScope}s and their {@link DependencyProvider}s.
 */
public class DependencyQuery<T> {

    public enum Mode {
        FIRST_MATCHING_DEPENDENCY,
        ALL_MATCHING_DEPENDENCIES
    }

    private final Class<T> mDependencyType;
    private final HashMap<Class<?>, T> mFoundDependencies;
    private final Mode mMode;

    public DependencyQuery(final Class<T> dependencyType) {
        this(dependencyType, Mode.FIRST_MATCHING_DEPENDENCY);
    }

    public DependencyQuery(final Class<T> dependencyType, final Mode mode) {
        mDependencyType = dependencyType;
        mFoundDependencies = new HashMap<>();
        mMode = mode;
    }

    @SuppressWarnings("unchecked")
    public static <T_DependencyType> DependencyQuery find(final Class<T_DependencyType> dependencyType) {
        return new DependencyQuery(dependencyType, Mode.FIRST_MATCHING_DEPENDENCY);
    }

    @SuppressWarnings("unchecked")
    public static <T_DependencyType> DependencyQuery findAll(final Class<T_DependencyType> dependencyType) {
        return new DependencyQuery(dependencyType, Mode.ALL_MATCHING_DEPENDENCIES);
    }

    public Class<T> getDependencyType() {
        return mDependencyType;
    }

    public Collection<T> getFoundDependencies() {
        return mFoundDependencies.values();
    }

    /**
     * Adds the found dependencies to the given {@link HashSet}.
     * param cache A {@link HashSet} for adding the found dependencies.
     * REMOVE
    public void getFoundDependencies(final HashSet<T> cache) {
        for (final T foundDependency : mFoundDependencies) {
            if (!containsInstanceOf(cache, foundDependency)) {
                cache.add(foundDependency);
            }
        }
    }

    private boolean containsInstanceOf(final HashSet<T> objects, final T object) {
        final Class<?> objectClass = object.getClass();

        for (final T existingObject : objects) {
            if (objectClass.equals(existingObject.getClass())) {
                return true;
            }
        }
        return false;
    }*/

    public T getFoundDependency() {
        final Collection<T> dependencies = mFoundDependencies.values();
        return dependencies.iterator().next();
    }

    @SuppressWarnings("unused")
    public Mode getMode() {
        return mMode;
    }

    public boolean foundDependencies() {
        return !mFoundDependencies.isEmpty();
    }

    public boolean add(final T dependency) {
        mFoundDependencies.put(dependency.getClass(), dependency);
        return (mMode == Mode.FIRST_MATCHING_DEPENDENCY);
    }

    public boolean isMatchingType(Class<?> type) {
        return mDependencyType.isAssignableFrom(type);
    }

    public boolean matches(final Class<?> providedType, final Class<?> concreteType) {
        return mDependencyType.isAssignableFrom(providedType) && !mFoundDependencies.containsKey(concreteType);
    }
}
