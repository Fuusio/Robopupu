package com.robopupu.api.dependency;

import java.util.HashMap;

/**
 * {@link DependencyMap} extends {@link HashMap} to implement a map for storing dependencies that
 * are accessible using {@link String} based keys.
 */
public class DependencyMap extends HashMap<String, Object> {

    /**
     * Stores the given dependency using the given key. A {@code null} dependency is not saved.
     * @param key The key as a {@link String}.
     * @param dependency A dependency.
     */
    public void addDependency(final String key, final Object dependency) {
        if (dependency != null) {
            put(key, dependency);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getDependency(final String key) {
        return (T)get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T removeDependency(final String key) {
        return (T)remove(key);
    }
}
