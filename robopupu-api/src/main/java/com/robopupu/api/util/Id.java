package com.robopupu.api.util;

/**
 * {@link Id} can be used to generate unique {@code long} type based IDs.
 */
public class Id {

    private static long seed = System.currentTimeMillis();;

    public static long next() {
        return seed++;
    }
}