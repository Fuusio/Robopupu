package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link SkipNode} TODO
 * @param <IN>
 */
public class SkipNode<IN> extends AbstractNode<IN, IN> {

    private int mStepCounter;
    private int mSteps;

    public SkipNode(final int steps) {
        mSteps = steps;
        mStepCounter = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> outputNode, final IN input) {
        if (mStepCounter >= mSteps) {
            return input;
        } else {
            mStepCounter++;
            return null;
        }
    }
}
