package com.robopupu.api.graph.actions;

public interface Action1<IN> {

    /**
     * Executes an {@link Action1} using the given input.
     * @param input An input value of type {@code IN}.
     */
    void execute(IN input);
}
