package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ByteNode} converts the input value to a {@link Byte}.
 * @param <IN> The input type.
 */
public class ByteNode<IN> extends Node<IN, Byte> {

    private byte value;

    @Override
    protected Byte processInput(final OutputNode<IN> source, final IN input) {
        if (input instanceof Number) {
            value = ((Number)input).byteValue();
            return value;
        } else {
            dispatchError(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to byte value")));
            return null;
        }
    }

    public byte getValue() {
        return value;
    }
}
