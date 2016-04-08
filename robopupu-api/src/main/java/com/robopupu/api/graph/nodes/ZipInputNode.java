package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ZipInputNode} extends {@link AbstractNode} to define an interface for {@link Node}s that are
 * used as input nodes for {@link ZipNode}s.
 */
public class ZipInputNode<IN, OUT>  extends AbstractNode<IN, OUT> {

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
    public void onInput(final OutputNode<IN> outputNode, final IN input) {
        mZipNode.onInput(this, input);
    }
}
