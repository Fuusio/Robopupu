package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ListNode} either emits all the values from a given {@link List} (immutable version), or
 * the input values that are received and stored to a  {@link List} until {@link Node#onCompleted(OutputNode)}
 * is invoked (mutable version).
 */
public class ListNode<IN> extends Node<IN, IN> {

    protected ArrayList<IN> mList;
    protected boolean mMutableList;

    public ListNode() {
        mList = new ArrayList<>();
        mMutableList = true;
    }

    public ListNode(final List<IN> list) {
        mList = new ArrayList<>();
        mList.addAll(list);
        mMutableList = list.isEmpty();
    }

    public List<IN> getList() {
        return mList;
    }

    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (mMutableList) {
            mList.add(input);
        }
        return null;
    }

    /**
     * Invoked to emit the contents of this {@link ListNode}.
     */
    @Override
    public void emitOutput() {
        for (final IN output : mList) {
            emitOutput(output);
        }

        if (mMutableList) {
            mList.clear();
        }
        completed(this);
    }

    @Override
    protected void doOnReset() {
        if (mMutableList) {
            mList.clear();
        }
    }

    @Override
    public void onCompleted(final OutputNode<?> source) {
        for (final IN output : mList) {
            emitOutput(output);
        }
    }
}
