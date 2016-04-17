package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link MergeNode} takes any number of {@link OutputNode}s and emits the input values received
 * from them in whatever order they are received.
 */
public class MergeNode<IN> extends Node<IN, IN> {

    protected final int mSourcesCount;
    protected final OutputNode<IN>[] mSources;

    @SafeVarargs
    public MergeNode(final OutputNode<IN>... sources) {
        mSources = sources;
        mSourcesCount = sources.length;

        for (int i = 0; i < mSourcesCount; i++) {
            mSources[i].attach(this);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        return input;
    }
}
