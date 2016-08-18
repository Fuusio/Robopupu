package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link TakeNode} takes defined number of input values, emits them, and ignores the rest input
 * values.
 */
public class TakeNode<IN> extends Node<IN, IN> {

    private int counter;
    private int steps;

    public TakeNode(final int steps) {
        this.steps = steps;
        counter = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> outputNode, final IN input) {
        if (counter >= steps) {
            onCompleted(this);
            return null;
        } else {
            counter++;
            return input;
        }
    }

    @Override
    public void doOnReset() {
        counter = 0;
    }
}
