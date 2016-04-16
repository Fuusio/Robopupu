package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link TakeNode} takes defined number of input values, emits them, and ignores the rest input
 * values.
 */
public class TakeNode<IN> extends Node<IN, IN> {

    private int mCounter;
    private int mSteps;

    public TakeNode(final int steps) {
        mSteps = steps;
        mCounter = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> outputNode, final IN input) {
        if (mCounter >= mSteps) {
            onCompleted(this);
            return null;
        } else {
            mCounter++;
            return input;
        }
    }

    @Override
    public void doOnReset() {
        mCounter = 0;
    }
}
