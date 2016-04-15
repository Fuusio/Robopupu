package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;
import java.util.List;

public class ListNode<IN> extends AbstractNode<IN, IN> {

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
    public void onInput(final OutputNode<IN> outputNode, final IN input) {
        if (mMutableList) {
            mList.add(input);
        }
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
}
