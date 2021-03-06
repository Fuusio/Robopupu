package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link RepeatNode} emits the received input value specified times as an output value.
 */
public class RepeatNode<IN> extends Node<IN, IN> {

    private int times;

    public RepeatNode() {
        times = Integer.MAX_VALUE;
    }

    public RepeatNode(final int times) {
        this.times = times;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            for (int i = 0; i < times; i++) {
                emitOutput(input);
            }
        }
        return null;
    }
}
