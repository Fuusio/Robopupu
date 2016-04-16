package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;

/**
 * {@link BufferNode} buffers a max amount of input values before emitting them as output values.
 */
public class BufferNode<IN> extends Node<IN, IN> {

    private ArrayList<IN> mBuffer;
    private int mCapacity;

    public BufferNode(final int capacity) {
        mBuffer = new ArrayList<>();
        mCapacity = capacity;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            if (mBuffer.size() < mCapacity) {
                mBuffer.add(input);
            } else {
                for (final IN output : mBuffer) {
                    emitOutput(output);
                }
                mBuffer.clear();
            }
        }
        return null;
    }

    @Override
    public void doOnReset() {
        mBuffer.clear();
    }
}
