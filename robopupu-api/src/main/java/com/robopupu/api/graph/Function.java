package com.robopupu.api.graph;

/**
 * Defines an function interface declaring functions objects and lambdas.
 */
public interface Function<IN, OUT> {

    /**
     * Evaluates a {@link Function} using the given input.
     * @param input An input value of type {@code IN}.
     * @return The output value of type {@code OUT}.
     */
    OUT eval(IN input);
}
