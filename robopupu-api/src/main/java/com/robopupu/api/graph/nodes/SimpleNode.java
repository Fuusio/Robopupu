package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

public class SimpleNode<IN, OUT> extends Node<IN, OUT> {

    /**
     * Constructs a new instance of {@link SimpleNode}.
     */
    public SimpleNode() {
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            try {
                return (OUT) input;
            } catch (ClassCastException e) {
                dispatchError(this, e);
            }
        }
        return null;
    }
}
