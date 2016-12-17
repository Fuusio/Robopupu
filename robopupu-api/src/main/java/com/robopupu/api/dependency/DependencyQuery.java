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

    private final Class<T> dependencyType;
    private final HashMap<Class<?>, T> foundDependencies;
    private final Mode mode;

    public DependencyQuery(final Class<T> dependencyType) {
        this(dependencyType, Mode.FIRST_MATCHING_DEPENDENCY);
    }

    public DependencyQuery(final Class<T> dependencyType, final Mode mode) {
        this.dependencyType = dependencyType;
        foundDependencies = new HashMap<>();
        this.mode = mode;
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
        return dependencyType;
    }

    public Collection<T> getFoundDependencies() {
        return foundDependencies.values();
    }

    public T getFoundDependency() {
        final Collection<T> dependencies = foundDependencies.values();
        return dependencies.iterator().next();
    }

    @SuppressWarnings("unused")
    public Mode getMode() {
        return mode;
    }

    public boolean foundDependencies() {
        return !foundDependencies.isEmpty();
    }

    public boolean add(final T dependency) {
        foundDependencies.put(dependency.getClass(), dependency);
        return (mode == Mode.FIRST_MATCHING_DEPENDENCY);
    }

    public boolean isMatchingType(Class<?> type) {
        return dependencyType.isAssignableFrom(type);
    }

    public boolean matches(final Class<?> providedType, final Class<?> concreteType) {
        return dependencyType.isAssignableFrom(providedType) && !foundDependencies.containsKey(concreteType);
    }
}
