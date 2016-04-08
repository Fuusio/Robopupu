package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ByteNode} converts the input value to a {@link Byte}.
 * @param <IN> The input type.
 */
public class ByteNode<IN> extends AbstractNode<IN, Byte> {

    private byte mValue;

    @Override
    protected Byte processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input instanceof Number) {
            mValue = ((Number)input).byteValue();
            return mValue;
        } else {
            error(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to byte value")));
            return null;
        }
    }

    public byte getValue() {
        return mValue;
    }
}
