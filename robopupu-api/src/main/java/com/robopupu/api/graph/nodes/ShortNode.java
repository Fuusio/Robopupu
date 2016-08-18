package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ShortNode} converts the input value to a {@link Short}.
 * @param <IN> The input type.
 */
public class ShortNode<IN> extends Node<IN, Short> {

    private short value;

    @Override
    protected Short processInput(final OutputNode<IN> source, final IN input) {
        if (input instanceof Number) {
            value = ((Number)input).shortValue();
            return value;
        } else {
            dispatchError(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to short value")));
            return null;
        }
    }

    public short getValue() {
        return value;
    }
}
