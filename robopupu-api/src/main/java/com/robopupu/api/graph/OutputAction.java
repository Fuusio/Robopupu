package com.robopupu.api.graph;

public interface OutputAction<OUT> {

    /**
     * Executes an {@link OutputAction}
     * @return An output value of type {@code OUT}.
     */
    OUT execute();
}
