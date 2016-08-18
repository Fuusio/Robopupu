package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;

public class ReverseNode<IN> extends Node<IN, IN> {

    private final ArrayList<IN> buffer;

    public ReverseNode() {
        buffer = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            buffer.add(input);
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> source) {
        final int size = buffer.size();

        if (size > 0) {
            for (int i = size - 1; i >= 0; i--) {
                emitOutput(buffer.get(i));
            }
        }
        completed(this);
    }
}
