package com.robopupu.api.graph;

import android.support.annotation.CallSuper;

/**
 * {@link AbstractInputNode} provide an abstract base class for implementing {@link InputNode}s.
 * @param <IN> The input type.
 */
public abstract class AbstractInputNode<IN> implements InputNode<IN> {

    protected boolean mErrorReceived;
    protected Graph<?> mGraph;

    @Override
    public void setGraph(final Graph<?> graph) {
        mGraph = graph;
    }

    /**
     * Constructs a new instance of {@link AbstractInputNode}.
     */
    protected AbstractInputNode() {
        mErrorReceived = false;
    }

    /**
     * Tests if an error has been received.
     * @return A {@code boolean}.
     */
    public boolean isErrorReceived() {
        return mErrorReceived;
    }

    /**
     * Sets an error to be received.
     * @return A {@code boolean}.
     */
    protected void setErrorReceived(boolean mErrorReceived) {
        this.mErrorReceived = mErrorReceived;
    }

    @CallSuper
    @Override
    public void onInput(final OutputNode<IN> source, final IN input) {
        if (!mErrorReceived) {
            doOnInput(source, input);
            processInput(source, input);
        }
    }

    /**
     * Invoked by {@link InputNode#onInput(OutputNode, Object)} when the given input {@link Object}
     * has been received from the {@link OutputNode} that emitted it. This method can be overridden
     * in subclasses for hooking the input received events.
     * @param source An {@link OutputNode} that emitted the input.
     * @param input The input {@link Object}.
     */
    protected void doOnInput(final OutputNode<IN> source, final IN input) {
        // By default do nothing
    }

    /**
     * Process the the given input to produce output. This method has to be overridden in subclassses.
     * Invoked by {@link InputNode#onInput(OutputNode, Object)} when the given input {@link Object}
     * has been received from the {@link OutputNode} that emitted it.
     * @param source An {@link OutputNode} that emitted the input.
     * @param input The input {@link Object}.
     */
    protected abstract void processInput(final OutputNode<IN> source, final IN input);

    @Override
    public void onCompleted(final OutputNode<?> source) {
        // By default do nothing
    }

    @CallSuper
    @Override
    public void onError(final OutputNode<?> source, final Throwable throwable) {
        mErrorReceived = true;
    }

    /**
     * Creates and formats an error message.
     * @param message The error message as a {@link String}. May contain place holders for formatting.
     * @param args Optional formats args.
     * @return The created error message as a {@link String}.
     */
    protected String createErrorMessage(final String message, final String... args) {
        final String formattedMessage = String.format(message, (Object[]) args);
        return "Error in nodes " + getClass().getSimpleName() + " : " + formattedMessage;
    }

    /**
     * Invoked by {@link AbstractOutputNode#onReset()}.
     */
    @CallSuper
    @Override
    public void onReset() {
        mErrorReceived = false;
    }
}
