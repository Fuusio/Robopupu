package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link IntNode} converts the input value to a {@link Integer}.
 * @param <IN> The input type.
 */
public class IntNode<IN> extends AbstractNode<IN, Integer> {

    private int mValue;

    @Override
    protected Integer processInput(final OutputNode<IN> source, final IN input) {
        if (input instanceof Number) {
            mValue = ((Number)input).intValue();
            return mValue;
        } else {
            dispatchError(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to int")));
            return null;
        }
    }

    public int getValue() {
        return mValue;
    }
}
