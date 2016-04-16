package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.graph.functions.BooleanFunction;

/**
 * {@link SkipWhileNode} skip input values while the assigned {@link BooleanFunction} evaluates to
 * true.
 */
public class SkipWhileNode<IN> extends Node<IN, IN> {

    private BooleanFunction<IN> mCondition;
    private boolean mSkippingEnded;

    public SkipWhileNode(final BooleanFunction<IN> condition) {
        setCondition(condition);
    }

    public void setCondition(final BooleanFunction condition) {
        mCondition = condition;
        mSkippingEnded = false;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null && !mSkippingEnded) {
            if (mCondition.eval(input)) {
                return null;
            } else {
                mSkippingEnded = true;
            }
        }
        return input;
    }
}
