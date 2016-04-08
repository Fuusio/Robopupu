package com.robopupu.api.graph.functions;

import com.robopupu.api.graph.Function;

/**
 * Defines an function interface declaring functions objects and lambdas.
 */
public interface Function2<IN1, IN2, OUT> {

    /**
     * Evaluates a {@link Function} using the given inputs.
     * @param input1 An input value of type {@code IN2}.
     * @param input2 An input value of type {@code IN2}.
     * @return The output value of type {@code OUT}.
     */
    OUT eval(IN1 input1, IN2 input2);
}
