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

    protected final ArrayList[] mBuffers;
    protected final boolean[] mCompleted;
    protected final int mSourcesCount;
    protected final HashMap<OutputNode, Integer> mSourceIndeces;
    protected final OutputNode<IN>[] mSources;

    protected int mCompletedIndex;

    @SafeVarargs
    public ConcatNode(final OutputNode<IN>... sources) {
        mSources = sources;
        mSourcesCount = sources.length;
        mSourceIndeces = new HashMap<>();
        mCompletedIndex = 0;

        mBuffers = new ArrayList[mSourcesCount];
        mCompleted = new boolean[mSourcesCount];

        for (int i = 0; i < mSourcesCount; i++) {
            mBuffers[i] = new ArrayList<>();
            mSources[i].attach(this);
            mSourceIndeces.put(mSources[i], i);
            mCompleted[i] = false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null && mSourceIndeces.containsKey(source)) {
            final int index = mSourceIndeces.get(source);
            ((List<IN>)mBuffers[index]).add(input);
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> source) {
        if (mSourceIndeces.containsKey(source)) {
            final int index = mSourceIndeces.get(source);

            if (mCompletedIndex == index) {
                emitBufferedValuesFor(index);
            } else {
                mCompleted[index] = true;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void emitBufferedValuesFor(final int index) {
        for (final IN value : (List<IN>)mBuffers[index]) {
            emitOutput(value);
        }
        mBuffers[index].clear();
        mCompleted[index] = false;
        mCompletedIndex++;

        if (mCompletedIndex >= mSourcesCount) {
            mCompletedIndex = 0;
        }

        if (mCompleted[mCompletedIndex]) {
            emitBufferedValuesFor(mCompletedIndex);
        }
    }

}
