package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ZipInputNode} is used as a specific input node for a {@link ZipNode}.
 */
public class ZipInputNode<IN, OUT>  extends Node<IN, OUT> {

    private final int index;
    private final ZipNode<OUT> zipNode;

    public ZipInputNode(final ZipNode<OUT> zipNode, final int index) {
        this.zipNode = zipNode;
        this.index = index;
        this.zipNode.addZipInputNode(this, index);
    }

    public int getIndex() {
        return index;
    }

    public ZipNode<OUT> getZipNode() {
        return zipNode;
    }

    @Override
    protected OUT processInput(final OutputNode<IN> source, final IN input) {
        zipNode.onInput(this, input);
        return null;
    }
}
