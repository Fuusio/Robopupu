package com.robopupu.api.util;

/**
 * {@link Utils} is a generic utility class that provides various utilities and convenience methods.
 */
public final class Utils {

    /**
     * Creates a {@link String}-based tag from a given {@link Class} that can be used as a tag in
     * logging methods of {@link android.util.Log}.
     * @param loggedClass A {@link Class}.
     * @return A created tag as a {@link String}.
     */
    public static String tag(final Class<?> loggedClass) {
        return loggedClass.getSimpleName();
    }
}
