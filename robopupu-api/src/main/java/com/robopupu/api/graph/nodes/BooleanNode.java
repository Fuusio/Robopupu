package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link BooleanNode} converts the input value to a {@link Boolean}.
 * @param <IN> The input type.
 */
public class BooleanNode<IN> extends Node<IN, Boolean> {

    private boolean value;

    @Override
    protected Boolean processInput(final OutputNode<IN> source, final IN input) {
        if (input instanceof Boolean) {
            value = (Boolean)input;
            return value;
        } else {
            dispatchError(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to Boolean")));
            return null;
        }
    }

    public boolean getValue() {
        return value;
    }
}
