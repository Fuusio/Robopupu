package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.NodeObserver;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ObserverNode} takes an {@link NodeObserver} for which it delegates invocations to methods
 * {@link Node#onInput(OutputNode, Object)}, {@link Node#onCompleted(OutputNode)},
 * {@link Node#onError(OutputNode, Throwable)}, and {@link Node#onReset()}.
 */
public class ObserverNode<IN> extends Node<IN, IN> {

    private NodeObserver<IN> observer;

    public ObserverNode(final NodeObserver<IN> observer) {
        this.observer = observer;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        observer.onInput(this, input);
        return input;
    }

    @Override
    public void onCompleted(final OutputNode<?> source) {
        observer.onCompleted(this);
    }

    @Override
    protected void doOnError(final OutputNode<?> source, final Throwable throwable) {
        observer.onError(this, throwable);
    }

    @Override
    protected void doOnReset() {
        observer.onReset(this);
    }
}
