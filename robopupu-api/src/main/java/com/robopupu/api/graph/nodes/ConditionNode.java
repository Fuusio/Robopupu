package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.graph.functions.BooleanFunction;

/**
 * {@link ConditionNode} converts the received input value to {@link Boolean} output value according
 * to assigned {@link BooleanFunction}.
 */
public class ConditionNode<IN> extends Node<IN, Boolean> {

    private BooleanFunction<IN> condition;

    public ConditionNode(final BooleanFunction<IN> condition) {
        setCondition(condition);
    }

    public void setCondition(final BooleanFunction condition) {
        this.condition = condition;
    }

    @Override
    protected Boolean processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            if (condition != null) {
                return condition.eval(input);
            }
        }
        return null;
    }
}
