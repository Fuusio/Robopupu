package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.InputNode;
import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;

/**
 * {@link ZipNode} extends {@link Node} to define an interface for {@link Node}s that are used
 * as input nodes for {@link ZipNode}s.
 */
public abstract class ZipNode<OUT> extends AbstractNode<Void, OUT> {

    protected final ArrayList[] mBuffers;
    protected final int mInputCount;
    protected final ZipInputNode[] mInputs;

    protected ZipNode(final int inputCount) {
        mInputCount = inputCount;
        mInputs = new ZipInputNode[inputCount];

        mBuffers = new ArrayList[inputCount];

        for (int i = 0; i < inputCount; i++) {
            mBuffers[i] = new ArrayList<>();
        }
    }

    protected void addZipInputNode(final ZipInputNode<?, OUT> inputNode, final int index) {
        mInputs[index] = inputNode;
    }

    @SuppressWarnings("unchecked")
    protected <IN> void onInput(final ZipInputNode<IN, OUT> inputNode, final IN input) {
        final ArrayList<IN> buffer = (ArrayList<IN>)mBuffers[inputNode.getIndex()];
        buffer.add(input);

        if (isReadyToZip()) {
            out(zip());
        }
    }

    @Override
    public void onCompleted(final OutputNode<?> outputNode) { // TODO
        for (final InputNode<OUT> inputNode : mInputNodes) {
            inputNode.onCompleted(outputNode);
        }
    }

    @Override
    public void onError(final OutputNode<?> outputNode, final Throwable throwable) {
        for (final InputNode<OUT> inputNode : mInputNodes) {
            inputNode.onError(outputNode, throwable);
        }
    }

    /**
     * Combines the input value to produce a new output value.
     * @return The combined output value of type {@code OUT}.
     */
    protected abstract OUT zip();

    /**
     * Tests of this {@link ZipNode} is ready to combine the inputs to produce a new output value.
     * @return A {@code boolean} value.
     */
    protected boolean isReadyToZip() {
        for (int i = 0; i < mInputCount; i++) {
            if (mBuffers[i].size() == 0) {
                return false;
            }
        }
        return true;
    }
}
