package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link NthNode} takes one input with the specified index. Indexing starts from 1.
 * @param <IN>
 */
public class NthNode<IN> extends Node<IN, IN> {

    private int counter;
    private int index;

    public NthNode(final int index) {
        this.index = index;
        counter = 1;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (counter == index) {
            return input;
        } else {
            counter++;
            return null;
        }
    }
}
