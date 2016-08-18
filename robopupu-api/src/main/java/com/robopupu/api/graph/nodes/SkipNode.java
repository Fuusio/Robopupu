package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link SkipNode} TODO
 * @param <IN>
 */
public class SkipNode<IN> extends Node<IN, IN> {

    private int counter;
    private int steps;

    public SkipNode(final int steps) {
        this.steps = steps;
        counter = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (counter >= steps) {
            return input;
        } else {
            counter++;
            return null;
        }
    }

    @Override
    public void doOnReset() {
        counter = 0;
    }
}
