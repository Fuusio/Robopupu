package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

public class SimpleNode<IN, OUT> extends AbstractNode<IN, OUT> {

    /**
     * Constructs a new instance of {@link SimpleNode}.
     */
    public SimpleNode() {
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input != null) {
            try {
                return (OUT) input;
            } catch (ClassCastException e) {
                error(this, e);
            }
        }
        return null;
    }
}
