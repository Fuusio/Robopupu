package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Function;
import com.robopupu.api.graph.Node;

import java.util.ArrayList;

/**
 * A{@link ZipNode} has 2 to 9 {@link ZipInputNode}s from which it receives input values and combines
 * them to produce an output value using a provided {@link Function}. {@link ZipNode} is an abstract
 * base class for concrete classes: {@link Zip2Node}, {@link Zip3Node}, {@link Zip4Node},
 * {@link Zip5Node}, {@link Zip6Node}, {@link Zip7Node}, {@link Zip8Node}, and {@link Zip9Node}.
 */
public abstract class ZipNode<OUT> extends Node<Void, OUT> {

    protected final ArrayList[] buffers;
    protected final int inputCount;
    protected final ZipInputNode[] inputs;

    protected ZipNode(final int inputCount) {
        this.inputCount = inputCount;
        inputs = new ZipInputNode[inputCount];

        buffers = new ArrayList[inputCount];

        for (int i = 0; i < inputCount; i++) {
            buffers[i] = new ArrayList<>();
        }
    }

    protected void addZipInputNode(final ZipInputNode<?, OUT> inputNode, final int index) {
        inputs[index] = inputNode;
    }

    @SuppressWarnings("unchecked")
    protected <IN> void onInput(final ZipInputNode<IN, OUT> inputNode, final IN input) {
        final ArrayList<IN> buffer = (ArrayList<IN>) buffers[inputNode.getIndex()];
        buffer.add(input);

        if (isReadyToZip()) {
            emitOutput(zip());
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
        for (int i = 0; i < inputCount; i++) {
            if (buffers[i].size() == 0) {
                return false;
            }
        }
        return true;
    }
}
