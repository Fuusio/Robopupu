package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.InputNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link SumNode} sums the reveived input values until {@link InputNode#onCompleted(OutputNode)}
 * is invoked.
 */
public class SumNode<IN> extends AbstractNode<IN, Double> {

    private double mSum;

    @SuppressWarnings("unchecked")
    @Override
    protected Double processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input instanceof Number) {
            mSum += ((Number)input).doubleValue();
        } else {
            error(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to Number")));
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> outputNode) {
        out(mSum);
    }
}
