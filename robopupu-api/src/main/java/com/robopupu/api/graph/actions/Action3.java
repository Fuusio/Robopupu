package com.robopupu.api.graph.actions;

import com.robopupu.api.graph.Action;

public interface Action3<IN1, IN2, IN3> {

    /**
     * Executes an {@link Action} using the given inputs.
     * @param input1 An input value of type {@code IN1}.
     * @param input2 An input value of type {@code IN2}.
     * @param input3 An input value of type {@code IN3}.
     */
    void execute(IN1 input1, IN2 input2, IN3 input3);
}
