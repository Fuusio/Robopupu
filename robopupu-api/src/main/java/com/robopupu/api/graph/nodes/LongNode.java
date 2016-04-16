package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link LongNode} converts the input value to a {@link Long}.
 * @param <IN> The input type.
 */
public class LongNode<IN> extends Node<IN, Long> {

    private long mValue;

    @Override
    protected Long processInput(final OutputNode<IN> source, final IN input) {
        if (input instanceof Number) {
            mValue = ((Number)input).longValue();
            return mValue;
        } else {
            dispatchError(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to Long")));
            return null;
        }
    }

    public long getValue() {
        return mValue;
    }
}
