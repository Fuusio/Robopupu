package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link FloatNode} converts the input value to a {@link Float}.
 * @param <IN> The input type.
 */
public class FloatNode<IN> extends Node<IN, Float> {

    private float value;

    @Override
    protected Float processInput(final OutputNode<IN> source, final IN input) {
        if (input instanceof Number) {
            value = ((Number)input).floatValue();
            return value;
        } else {
            dispatchError(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to Float")));
            return null;
        }
    }

    public float getValue() {
        return value;
    }
}
