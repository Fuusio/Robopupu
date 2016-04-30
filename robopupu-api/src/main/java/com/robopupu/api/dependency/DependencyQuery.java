package com.robopupu.api.dependency;

import java.util.ArrayList;
import java.util.List;

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
    private final List<T> mFoundDependencies;
    private final Mode mMode;

    public DependencyQuery(final Class<T> dependencyType) {
        this(dependencyType, Mode.FIRST_MATCHING_DEPENDENCY);
    }

    public DependencyQuery(final Class<T> dependencyType, final Mode mode) {
        mDependencyType = dependencyType;
        mFoundDependencies = new ArrayList<>();
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

    public List<T> getFoundDependencies() {
        return mFoundDependencies;
    }

    @SuppressWarnings("unchecked")
    public <T> T getFoundDependency() {
        return (T) mFoundDependencies.get(0);
    }

    public Mode getMode() {
        return mMode;
    }

    public boolean foundDependencies() {
        return !mFoundDependencies.isEmpty();
    }

    public boolean add(final T dependency) {
        mFoundDependencies.add(dependency);
        return (mMode == Mode.FIRST_MATCHING_DEPENDENCY);
    }

    public boolean matches(final Class<?> providedType) {
        return mDependencyType.isAssignableFrom(providedType);
    }
}
