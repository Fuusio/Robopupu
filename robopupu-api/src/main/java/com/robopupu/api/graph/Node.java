package com.robopupu.api.graph;

public interface Node<IN, OUT> extends InputNode<IN>, OutputNode<OUT> {

    /**
     * Invoked by the input {@link Node} for this {@link Node} to receive the input {@link Object}.
     * @param input The input {@link Object}.
     */
    void onInput(IN input);
}
