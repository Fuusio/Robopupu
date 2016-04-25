package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.InputNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ConcatStringsNode} combines the received input strings in a {@link StringBuilder} until
 * {@link InputNode#onCompleted(OutputNode)} is invoked. The resulting combined string is then
 * emitted as an output value.
 */
public class ConcatStringsNode<IN> extends Node<IN, String> {

    private final StringBuilder mString;

    public ConcatStringsNode() {
        mString = new StringBuilder();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            mString.append(input.toString());
        } else {
            dispatchError(this, new ClassCastException(createErrorMessage("Received an input value that cannot be converted to String")));
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> outputNode) {
        emitOutput(mString.toString());
    }
}