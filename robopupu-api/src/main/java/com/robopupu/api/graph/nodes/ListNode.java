package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.AbstractOutputNode;
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
    public void emit() {
        for (final IN output : mList) {
            out(output);
        }

        if (mMutableList) {
            mList.clear();
        } else {
            completed(this);
        }
    }
}
