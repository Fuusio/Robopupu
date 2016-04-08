package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.graph.functions.BooleanFunction;

public class ConditionNode<IN> extends AbstractNode<IN, Boolean> {

    private BooleanFunction<IN> mCondition;

    public ConditionNode(final BooleanFunction<IN> condition) {
        setCondition(condition);
    }

    public void setCondition(final BooleanFunction condition) {
        mCondition = condition;
    }

    @Override
    protected Boolean processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input != null) {
            if (mCondition != null) {
                return mCondition.eval(input);
            }
        }
        return null;
    }
}
