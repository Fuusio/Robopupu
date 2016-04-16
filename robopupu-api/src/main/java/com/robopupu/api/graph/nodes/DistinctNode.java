package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * {@link DistinctNode} emits distinct values buffered until {@link Node#onCompleted(OutputNode)} is
 * invoked.
 */
public class DistinctNode<IN> extends Node<IN, IN> {

    private ArrayList<IN> mBuffer;
    private HashMap<IN, IN> mMapped;

    public DistinctNode() {
        mBuffer = new ArrayList<>();
        mMapped = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            if (!mMapped.containsKey(input)) {
                mMapped.put(input, input);
                mBuffer.add(input);
            }
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> outputNode) {
        for (final IN output : mBuffer) {
            emitOutput(output);
        }
        completed(this);
    }
}
