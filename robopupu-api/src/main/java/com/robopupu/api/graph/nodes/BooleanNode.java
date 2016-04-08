package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link BooleanNode} converts the input value to a {@link Boolean}.
 * @param <IN> The input type.
 */
public class BooleanNode<IN> extends AbstractNode<IN, Boolean> {

    private boolean mValue;

    @Override
    protected Boolean processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input instanceof Boolean) {
            mValue = (Boolean)input;
            return mValue;
        } else {
            error(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to Boolean")));
            return null;
        }
    }

    public boolean getValue() {
        return mValue;
    }
}
