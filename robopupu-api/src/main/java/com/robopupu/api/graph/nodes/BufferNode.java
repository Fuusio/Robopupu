package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;

/**
 * {@link BufferNode} buffers a max amount of input values before emitting them as output values.
 */
public class BufferNode<IN> extends Node<IN, IN> {

    private ArrayList<IN> buffer;
    private int capacity;

    public BufferNode(final int capacity) {
        buffer = new ArrayList<>();
        this.capacity = capacity;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            if (buffer.size() < capacity) {
                buffer.add(input);
            } else {
                for (final IN output : buffer) {
                    emitOutput(output);
                }
                buffer.clear();
            }
        }
        return null;
    }

    @Override
    public void doOnReset() {
        buffer.clear();
    }
}
