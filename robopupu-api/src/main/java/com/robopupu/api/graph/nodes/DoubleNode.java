package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link DoubleNode} converts the input value to a {@link Double}.
 * @param <IN> The input type.
 */
public class DoubleNode<IN> extends Node<IN, Double> {

    private double value;

    @Override
    protected Double processInput(final OutputNode<IN> source, final IN input) {
        if (input instanceof Number) {
            value = ((Number)input).doubleValue();
            return value;
        } else {
            dispatchError(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to Double")));
            return null;
        }
    }

    public double getValue() {
        return value;
    }
}
