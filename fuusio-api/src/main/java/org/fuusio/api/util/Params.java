package org.fuusio.api.util;

import android.os.Bundle;

import java.util.HashMap;

/**
 * {@link Params} extends {@link HashMap} to provide a convenience object for passing parameters.
 */
public class Params extends HashMap<String, Object> {

    public Params() {
    }

    public Params(final Bundle bundle) {
        if (bundle != null) {
            for (final String key : bundle.keySet()) {
                put(key, bundle.get(key));
            }
        }
    }

    public Params(final String key, final Object value) {
        put(key, value);
    }

    /**
     * Gets the value as a {@code boolean}.
     * @param key The key as a {@link String}.
     * @return A {@code boolean}.
     */
    @SuppressWarnings("unchecked")
    public boolean getBoolean(final String key) {
        return (Boolean) get(key);
    }

    /**
     * Gets the value as a {@code float}.
     * @param key The key as a {@link String}.
     * @return A {@code float}.
     */
    @SuppressWarnings("unchecked")
    public float getFloat(final String key) {
        return (Float) get(key);
    }

    /**
     * Gets the value as a {@code int}.
     * @param key The key as a {@link String}.
     * @return A {@code int}.
     */
    @SuppressWarnings("unchecked")
    public int getInt(final String key) {
        return (Integer) get(key);
    }
    /**
     * Gets the value as a {@code long}.
     * @param key The key as a {@link String}.
     * @return A {@code long}.
     */
    @SuppressWarnings("unchecked")
    public long getLong(final String key) {
        return (Long) get(key);
    }

    /**
     * Gets the value as a {@link String}.
     * @param key The key as a {@link String}.
     * @return A {@link String}.
     */
    @SuppressWarnings("unchecked")
    public String getString(final String key) {
        return (String) get(key);
    }
    /**
     * Adds the given {@link Params} to this {@link Params}.
     * @param params A {@link Params}. May be {@code null}.
     */
    public void add(final Params params) {
        if (params != null) {
            for (final String key : params.keySet()) {
                put(key, params.get(key));
            }
        }
    }

    /**
     * Create an instance of {@link Params} from the given {@link Bundle}.
     * @param bundle A {@link Bundle}. May be {@code null}.
     * @return The created instance of {@link Params} if {@code bundle} was not null; otherwise
     * {@code null}.
     */
    public static Params from(final Bundle bundle) {
        return new Params(bundle);
    }

    /**
     * Merges the given array of {@link Params} to single instance of {@link Params}.
     * @param params An array of {@link Params}. May be {@code null}.
     * @return A {@link Params}. May return {@code null}.
     */
    public static Params merge(final Params[] params) {

        if (params != null) {
            final int length = params.length;

            if (length == 1) {
                return params[0];
            } else {
                final Params mergedParams = new Params();

                for (int i = length - 1; i >= 0; i--) {
                    mergedParams.add(params[i]);
                }
                return mergedParams;
            }
        }

        return null;
    }
}
