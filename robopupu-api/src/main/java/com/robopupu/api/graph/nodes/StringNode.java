package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link StringNode} converts the input value to a {@link String}.
 * @param <IN> The input type.
 */
public class StringNode<IN> extends Node<IN, String> {

    private String value;

    @Override
    protected String processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input != null) {
            value = input.toString();
            return value;
        } else {
            dispatchError(this, new NullPointerException(createErrorMessage("Received a null object")));
            return null;
        }
    }

    public String getValue() {
        return value;
    }
}
