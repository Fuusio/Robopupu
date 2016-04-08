package com.robopupu.api.graph;

/**
 * {@link Tag} is a utility object that can be used for tagging {@link Node}s added to
 * a {@link Graph}.
 */
public class Tag<T> {

    /**
     * Creates a new instance of {@link Tag}.
     * @param <OUT> The type of the tagged {@link Node}.
     * @return A new instance of  {@link Tag}.
     */
    public static <OUT> Tag<OUT> create() {
        return new Tag<>();
    }
}
