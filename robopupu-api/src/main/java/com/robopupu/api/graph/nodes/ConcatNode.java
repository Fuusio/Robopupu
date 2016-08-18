package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link ConcatNode} takes any number of {@link OutputNode}s and emits the input values received
 * from them one after the other, without interleaving the values.
 */
public class ConcatNode<IN> extends Node<IN, IN> {

    protected final ArrayList[] buffers;
    protected final boolean[] completed;
    protected final int sourcesCount;
    protected final HashMap<OutputNode, Integer> sourceIndeces;
    protected final OutputNode<IN>[] sources;

    protected int mCompletedIndex;

    @SafeVarargs
    public ConcatNode(final OutputNode<IN>... sources) {
        this.sources = sources;
        sourcesCount = sources.length;
        sourceIndeces = new HashMap<>();
        mCompletedIndex = 0;

        buffers = new ArrayList[sourcesCount];
        completed = new boolean[sourcesCount];

        for (int i = 0; i < sourcesCount; i++) {
            buffers[i] = new ArrayList<>();
            this.sources[i].attach(this);
            sourceIndeces.put(this.sources[i], i);
            completed[i] = false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null && sourceIndeces.containsKey(source)) {
            final int index = sourceIndeces.get(source);
            ((List<IN>) buffers[index]).add(input);
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> source) {
        if (sourceIndeces.containsKey(source)) {
            final int index = sourceIndeces.get(source);

            if (mCompletedIndex == index) {
                emitBufferedValuesFor(index);
            } else {
                completed[index] = true;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void emitBufferedValuesFor(final int index) {
        for (final IN value : (List<IN>) buffers[index]) {
            emitOutput(value);
        }
        buffers[index].clear();
        completed[index] = false;
        mCompletedIndex++;

        if (mCompletedIndex >= sourcesCount) {
            mCompletedIndex = 0;
        }

        if (completed[mCompletedIndex]) {
            emitBufferedValuesFor(mCompletedIndex);
        }
    }

}
