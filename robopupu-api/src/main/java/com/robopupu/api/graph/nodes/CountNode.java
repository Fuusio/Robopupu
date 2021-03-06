package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.InputNode;
import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link CountNode} counts the received input values until {@link InputNode#onCompleted(OutputNode)}
 * is invoked.
 */
public class CountNode<IN> extends Node<IN, Integer> {

    private int count;

    @SuppressWarnings("unchecked")
    @Override
    protected Integer processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            count++;
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> outputNode) {
        emitOutput(count);
    }

    @Override
    public void doOnReset() {
        count = 0;
    }
}
