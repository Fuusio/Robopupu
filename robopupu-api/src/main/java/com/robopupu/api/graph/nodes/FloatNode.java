package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link FloatNode} converts the input value to a {@link Float}.
 * @param <IN> The input type.
 */
public class FloatNode<IN> extends AbstractNode<IN, Float> {

    private float mValue;

    @Override
    protected Float processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input instanceof Number) {
            mValue = ((Number)input).floatValue();
            return mValue;
        } else {
            error(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to Float")));
            return null;
        }
    }

    public float getValue() {
        return mValue;
    }
}
