package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ZipInputNode} is used as a specific input node for a {@link ZipNode}.
 */
public class ZipInputNode<IN, OUT>  extends Node<IN, OUT> {

    private final int mIndex;
    private final ZipNode<OUT> mZipNode;

    public ZipInputNode(final ZipNode<OUT> zipNode, final int index) {
        mZipNode = zipNode;
        mIndex = index;
        mZipNode.addZipInputNode(this, index);
    }

    public int getIndex() {
        return mIndex;
    }

    public ZipNode<OUT> getZipNode() {
        return mZipNode;
    }

    @Override
    protected OUT processInput(final OutputNode<IN> source, final IN input) {
        mZipNode.onInput(this, input);
        return null;
    }
}
