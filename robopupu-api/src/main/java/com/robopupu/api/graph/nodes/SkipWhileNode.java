package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.graph.functions.BooleanFunction;

/**
 * {@link SkipWhileNode} skip input values while the assigned {@link BooleanFunction} evaluates to
 * true.
 */
public class SkipWhileNode<IN> extends Node<IN, IN> {

    private BooleanFunction<IN> condition;
    private boolean skippingEnded;

    public SkipWhileNode(final BooleanFunction<IN> condition) {
        setCondition(condition);
    }

    public void setCondition(final BooleanFunction condition) {
        this.condition = condition;
        skippingEnded = false;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null && !skippingEnded) {
            if (condition.eval(input)) {
                return null;
            } else {
                skippingEnded = true;
            }
        }
        return input;
    }
}
