package com.robopupu.api.graph.functions;

import com.robopupu.api.graph.Function;

/**
 * Defines an function interface declaring functions objects and lambdas.
 */
public interface Function7<IN1, IN2, IN3, IN4, IN5, IN6, IN7, OUT> {

    /**
     * Evaluates a {@link Function} using the given inputs.
     * @param input1 An input value of type {@code IN1}.
     * @param input2 An input value of type {@code IN2}.
     * @param input3 An input value of type {@code IN3}.
     * @param input4 An input value of type {@code IN4}.
     * @param input5 An input value of type {@code IN5}.
     * @param input6 An input value of type {@code IN6}.
     * @param input7 An input value of type {@code IN7}.
     */
    OUT eval(IN1 input1, IN2 input2, IN3 input3, IN4 input4, IN5 input5, IN6 input6, IN7 input7);
}
