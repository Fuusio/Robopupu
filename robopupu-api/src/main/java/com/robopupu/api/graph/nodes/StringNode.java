package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link StringNode} converts the input value to a {@link String}.
 * @param <IN> The input type.
 */
public class StringNode<IN> extends AbstractNode<IN, String> {

    private String mValue;

    @Override
    protected String processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input != null) {
            return input.toString();
        } else {
            error(this, new NullPointerException(createErrorMessage("Received a null object")));
            return null;
        }
    }

    public String getValue() {
        return mValue;
    }
}
