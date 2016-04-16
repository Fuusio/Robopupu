package com.robopupu.api.graph;

/**
 * {@link NodeObserver} defines an observer interface for receiving callbacks from
 * an {@link OutputNode}.
 * @param <OUT> The type of the output received from a {@link OutputNode}.
 */
public interface NodeObserver<OUT> {

    /**
     * Invoked when the given {@link OutputNode} has received the given input value.
     * @param node The @link OutputNode}.
     * @param input The input value of type {@code OUT}.
     */
    void onInput(OutputNode<OUT> node, OUT input);

    /**
     * Invoked when the given {@link OutputNode} is completed.
     * @param node The completed {@link OutputNode}.
     */
    void onCompleted(OutputNode<OUT> node);

    /**
     * Invoked when the given {@link OutputNode} has detected an error.
     * @param node The {@link OutputNode} notifying about error.
     * @param throwable A {@link Throwable} representing the detected error.
     */
    void onError(OutputNode<OUT> node, Throwable throwable);

    /**
     * Invoked when the given {@link OutputNode} has been resetted.
     */
    void onReset(OutputNode<OUT> node);
}
