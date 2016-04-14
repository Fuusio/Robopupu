package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;

public class BufferNode<IN> extends AbstractNode<IN, IN> {

    private ArrayList<IN> mBuffer;
    private int mCapacity;

    public BufferNode(final int capacity) {
        mBuffer = new ArrayList<>();
        mCapacity = capacity;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input != null) {
            if (mBuffer.size() < mCapacity) {
                mBuffer.add(input);
            } else {
                for (final IN output : mBuffer) {
                    out(output);
                }
                mBuffer.clear();
            }
        }
        return null;
    }

    @Override
    public void onReset() {
        super.onReset();
        mBuffer.clear();
    }
}
