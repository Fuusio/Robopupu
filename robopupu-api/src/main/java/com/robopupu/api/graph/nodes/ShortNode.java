package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ShortNode} converts the input value to a {@link Short}.
 * @param <IN> The input type.
 */
public class ShortNode<IN> extends AbstractNode<IN, Short> {

    private short mValue;

    @Override
    protected Short processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input instanceof Number) {
            mValue = ((Number)input).shortValue();
            return mValue;
        } else {
            error(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to short value")));
            return null;
        }
    }

    public short getValue() {
        return mValue;
    }
}
