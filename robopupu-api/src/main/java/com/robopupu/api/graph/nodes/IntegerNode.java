package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link IntegerNode} converts the input value to a {@link Integer}.
 * @param <IN> The input type.
 */
public class IntegerNode<IN> extends Node<IN, Integer> {

    private int value;

    @Override
    protected Integer processInput(final OutputNode<IN> source, final IN input) {
        if (input instanceof Number) {
            value = ((Number)input).intValue();
            return value;
        } else {
            dispatchError(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to int")));
            return null;
        }
    }

    public int getValue() {
        return value;
    }
}
