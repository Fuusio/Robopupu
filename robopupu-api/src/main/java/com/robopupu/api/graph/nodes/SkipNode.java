package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link SkipNode} TODO
 * @param <IN>
 */
public class SkipNode<IN> extends Node<IN, IN> {

    private int mCounter;
    private int mSteps;

    public SkipNode(final int steps) {
        mSteps = steps;
        mCounter = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (mCounter >= mSteps) {
            return input;
        } else {
            mCounter++;
            return null;
        }
    }

    @Override
    public void doOnReset() {
        mCounter = 0;
    }
}
