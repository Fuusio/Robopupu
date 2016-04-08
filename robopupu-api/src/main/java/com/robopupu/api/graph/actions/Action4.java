package com.robopupu.api.graph.actions;

import com.robopupu.api.graph.Action;

public interface Action4<IN1, IN2, IN3, IN4> {

    /**
     * Executes an {@link Action} using the given inputs.
     * @param input1 An input value of type {@code IN1}.
     * @param input2 An input value of type {@code IN2}.
     * @param input3 An input value of type {@code IN3}.
     * @param input4 An input value of type {@code IN4}.
     */
    void execute(IN1 input1, IN2 input2, IN3 input3, IN4 input4);
}
