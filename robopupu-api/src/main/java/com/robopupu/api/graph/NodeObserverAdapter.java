package com.robopupu.api.graph;

/**
 * {@link NodeObserverAdapter} is an adapter class for {@link NodeObserver}.
 */
public class NodeObserverAdapter<OUT> implements NodeObserver<OUT> {

    @Override
    public void onInput(OutputNode<OUT> node, OUT input) {
        // Do nothing
    }

    @Override
    public void onCompleted(OutputNode<OUT> node) {
        // Do nothing
    }

    @Override
    public void onError(OutputNode<OUT> node, Throwable throwable) {
        // Do nothing
    }

    @Override
    public void onReset(OutputNode<OUT> node) {
        // Do nothing
    }
}
