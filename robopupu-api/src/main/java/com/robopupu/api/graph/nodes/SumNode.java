package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.InputNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link SumNode} sums the received input values until {@link InputNode#onCompleted(OutputNode)}
 * is invoked.
 */
public class SumNode<IN> extends Node<IN, Double> {

    private double sum;

    @SuppressWarnings("unchecked")
    @Override
    protected Double processInput(final OutputNode<IN> source, final IN input) {
        if (input instanceof Number) {
            sum += ((Number)input).doubleValue();
        } else {
            dispatchError(this, new ClassCastException(createErrorMessage("Received an object that cannot be converted to Number")));
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> outputNode) {
        emitOutput(sum);
    }

    @Override
    public void doOnReset() {
        sum = 0;
    }
}
