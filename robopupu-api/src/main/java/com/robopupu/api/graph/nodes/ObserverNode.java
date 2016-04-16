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

    private NodeObserver<IN> mObserver;

    public ObserverNode(final NodeObserver<IN> observer) {
        mObserver = observer;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        mObserver.onInput(this, input);
        return input;
    }

    @Override
    public void onCompleted(final OutputNode<?> source) {
        mObserver.onCompleted(this);
    }

    @Override
    protected void doOnError(final OutputNode<?> source, final Throwable throwable) {
        mObserver.onError(this, throwable);
    }

    @Override
    protected void doOnReset() {
        mObserver.onReset(this);
    }
}
