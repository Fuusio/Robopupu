package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.graph.functions.BooleanFunction;

public class FilterNode<IN> extends AbstractNode<IN, IN> {

    private BooleanFunction<IN> mCondition;

    public FilterNode() {
    }

    public FilterNode(final BooleanFunction<IN> condition) {
        setCondition(condition);
    }

    public void setCondition(final BooleanFunction condition) {
        mCondition = condition;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input != null) {
            if (mCondition != null) {
                if (mCondition.eval(input)) {
                    return input;
                }
            } else if (accepts(input)) {
                return input;
            }
        }
        return null;
    }

    /**
     * Override this method to implement a condition for filtering.
     * @param input
     * @return A {@link boolean} value.
     */
    protected boolean accepts(final IN input) {
        return false;
    }
}
