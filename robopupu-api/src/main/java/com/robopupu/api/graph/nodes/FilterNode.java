package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.graph.functions.BooleanFunction;

/**
 * {@link FilterNode} filters the received input values as specified by the assigned
 * {@link BooleanFunction}.
 */
public class FilterNode<IN> extends Node<IN, IN> {

    private BooleanFunction<IN> condition;

    public FilterNode() {
    }

    public FilterNode(final BooleanFunction<IN> condition) {
        setCondition(condition);
    }

    public void setCondition(final BooleanFunction condition) {
        this.condition = condition;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            if (condition != null) {
                if (condition.eval(input)) {
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
