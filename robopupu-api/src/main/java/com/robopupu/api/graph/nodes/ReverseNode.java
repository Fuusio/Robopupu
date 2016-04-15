package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;

public class ReverseNode<IN> extends AbstractNode<IN, IN> {

    private final ArrayList<IN> mBuffer;

    public ReverseNode() {
        mBuffer = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input != null) {
            mBuffer.add(input);
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> outputNode) {
        final int size = mBuffer.size();

        if (size > 0) {
            for (int i = size - 1; i >= 0; i--) {
                emitOutput(mBuffer.get(i));
            }
        }
        completed(this);
    }
}
