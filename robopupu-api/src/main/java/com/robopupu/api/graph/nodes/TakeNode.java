package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link TakeNode} TODO
 * @param <IN>
 */
public class TakeNode<IN> extends AbstractNode<IN, IN> {

    private int mStepCounter;
    private int mSteps;

    public TakeNode(final int steps) {
        mSteps = steps;
        mStepCounter = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> outputNode, final IN input) {
        if (mStepCounter >= mSteps) {
            onCompleted(this);
            return null;
        } else {
            mStepCounter++;
            return input;
        }
    }
}
