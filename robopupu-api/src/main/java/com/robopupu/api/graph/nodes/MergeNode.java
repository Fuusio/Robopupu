package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link MergeNode} takes any number of {@link OutputNode}s and emits the input values received
 * from them in whatever order they are received.
 */
public class MergeNode<IN> extends Node<IN, IN> {

    protected final int sourcesCount;
    protected final OutputNode<IN>[] sources;

    @SafeVarargs
    public MergeNode(final OutputNode<IN>... sources) {
        this.sources = sources;
        sourcesCount = sources.length;

        for (int i = 0; i < sourcesCount; i++) {
            this.sources[i].attach(this);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        return input;
    }
}
