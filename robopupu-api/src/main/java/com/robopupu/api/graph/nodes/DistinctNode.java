package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;
import java.util.HashMap;

public class DistinctNode<IN> extends AbstractNode<IN, IN> {

    private ArrayList<IN> mBuffer;
    private HashMap<IN, IN> mMapped;

    public DistinctNode() {
        mBuffer = new ArrayList<>();
        mMapped = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> outputNode, final IN input) {
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
            out(output);
        }
        completed(this);
    }
}
