package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link NthNode} takes one input with the specified index. Indexing starts from 1.
 * @param <IN>
 */
public class NthNode<IN> extends AbstractNode<IN, IN> {

    private int mCounter;
    private int mIndex;

    public NthNode(final int index) {
        mIndex = index;
        mCounter = 1;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> outputNode, final IN input) {
        if (mCounter == mIndex) {
            return input;
        } else {
            mCounter++;
            return null;
        }
    }
}
