package com.robopupu.api.graph;

public interface InputNode<IN> {

    /**
     * Invoked by an {@link OutputNode} for this {@link InputNode} to receive the input
     * {@link Object} that was emitted by the {@link OutputNode}.
     * @param outputNode The @link OutputNode}.
     * @param input The input {@link Object}.
     * @return  This {@link InputNode}.
     */
    void onInput(final OutputNode<IN> outputNode, final IN input);

    /**
     * Invoked when the specified input {@link OutputNode} is completed.
     * @param outputNode The completed {@link OutputNode}.
     */
    void onCompleted(final OutputNode<?> outputNode);

    /**
     * Invoked when the {@link OutputNode} has detected or received an error. The default
     * implementation detaches this {@link InputNode} from the {@link OutputNode} that notified about
     * an error and dispatched the error to attached {@link InputNode}s.
     * @param outputNode The {@link OutputNode} notifying about error.
     * @param throwable A {@link Throwable} representing the error.
     */
    void onError(final OutputNode<?> outputNode, final Throwable throwable);
}
