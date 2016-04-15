package com.robopupu.api.graph;

public interface InputNode<IN> {

    /**
     * Sets the {@link Graph} that contains this {@link InputNode}.
     * @param graph A {@link Graph}.
     */
    void setGraph(Graph<?> graph);

    /**
     * Invoked by the given source {@link OutputNode} for this {@link InputNode} to receive the input
     * {@link Object} that was emitted by the {@link OutputNode}.
     * @param source The @link OutputNode}.
     * @param input The input {@link Object}.
     * @return  This {@link InputNode}.
     */
    void onInput(OutputNode<IN> source, IN input);

    /**
     * Invoked when the specified source {@link OutputNode} is completed.
     * @param source The completed {@link OutputNode}.
     */
    void onCompleted(OutputNode<?> source);

    /**
     * Invoked when the given source {@link OutputNode} has detected an error.
     * @param source The {@link OutputNode} notifying about error.
     * @param throwable A {@link Throwable} representing the detected error.
     */
    void onError(OutputNode<?> source, Throwable throwable);

    /**
     * Invoked to reset this {@link InputNode}.
     */
    void onReset();
}
