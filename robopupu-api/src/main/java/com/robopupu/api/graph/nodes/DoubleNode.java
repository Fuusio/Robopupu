package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link DoubleNode} converts the input value to a {@link Double}.
 * @param <IN> The input type.
 */
public class DoubleNode<IN> extends AbstractNode<IN, Double> {

    private double mValue;

    @Override
    protected Double processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input instanceof Number) {
            mValue = ((Number)input).doubleValue();
            return mValue;
        } else {
            error(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to Double")));
            return null;
        }
    }

    public double getValue() {
        return mValue;
    }
}
