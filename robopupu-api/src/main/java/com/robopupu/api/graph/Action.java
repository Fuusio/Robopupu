package com.robopupu.api.graph;

public interface Action<IN> {

    /**
     * Executes an {@link Action} using the given input.
     * @param input An input value of type {@code IN}.
     */
    void execute(IN input);
}
