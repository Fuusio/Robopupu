package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link RepeatNode} TODO
 * @param <IN>
 */
public class RepeatNode<IN> extends AbstractNode<IN, IN> {

    private int mTimes;

    public RepeatNode(final int times) {
        mTimes = times;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input != null) {
            for (int i = 0; i < mTimes; i++) {
                out(input);
            }
        }
        return null;
    }
}
