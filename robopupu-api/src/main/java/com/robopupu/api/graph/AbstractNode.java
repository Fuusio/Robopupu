package com.robopupu.api.graph;

import android.support.annotation.CallSuper;

/**
 * {@link AbstractNode} provide an abstract base class for implementing {@link Node}s.
 * @param <IN> The input type.
 * @param <OUT> The output type.
 */
public abstract class AbstractNode<IN, OUT> extends AbstractOutputNode<OUT>
        implements Node<IN, OUT> {

    /**
     * Constructs a new instance of {@link AbstractNode}.
     */
    protected AbstractNode() {
    }

    @CallSuper
    @Override
    public void onInput(final OutputNode<IN> source, final IN input) {
        if (!mErrorReceived) {
            doOnInput(source, input);
            emitOutput(processInput(source, input));
        }
    }

    /**
     * Invoked by {@link InputNode#onInput(OutputNode, Object)} when the given input {@link Object}
     * has been received from the {@link OutputNode} that emitted it. This method can be overridden
     * in subclasses for hooking on the input received events.
     * @param source An {@link OutputNode} that emitted the input.
     * @param input The input {@link Object}.
     */
    protected void doOnInput(final OutputNode<IN> source, final IN input) {
        // By default do nothing
    }

    /**
     * Invoked by {@link InputNode#onInput(OutputNode, Object)} when the given input {@link Object}
     * has been received from the {@link OutputNode} that emitted it.
     * @param source An {@link OutputNode} that emitted the input.
     * @param input The input {@link Object}.
     */
    @SuppressWarnings("unchecked")
    protected OUT processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            try {
                return (OUT) input;
            } catch (ClassCastException e) {
                dispatchError(this, e);
            }
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> source) {
        doOnCompleted(source);

        for (final InputNode<OUT> inputNode : mInputNodes) {
            inputNode.onCompleted(source);
        }
    }

    /**
     * Invoked by {@link InputNode#onCompleted(OutputNode)}. This method can be overridden
     * in subclasses for hooking on the completed events.
     * @param source An {@link OutputNode} that was completed.
     */
    protected void doOnCompleted(final OutputNode<?> source) {
        // By default do nothing
    }

    @CallSuper
    @Override
    public void onError(final OutputNode<?> source, final Throwable throwable) {
        doOnError(source, throwable);
        dispatchError(source, throwable);
    }

    /**
     * Invoked by {@link InputNode#onCompleted(OutputNode)}. This method can be overridden
     * in subclasses for hooking on the error received events.
     * @param source The {@link OutputNode} notifying about error.
     * @param throwable A {@link Throwable} representing the error.
     */
    protected void doOnError(final OutputNode<?> source, final Throwable throwable) {
        // By default do nothing
    }
}
