package com.robopupu.api.graph.actions;

import com.robopupu.api.graph.Action;

public interface Action2<IN1, IN2> {

    /**
     * Executes an {@link Action} using the given inputs.
     * @param input1 An input value of type {@code IN1}.
     * @param input2 An input value of type {@code IN2}.
     */
    void execute(IN1 input1, IN2 input2);
}
