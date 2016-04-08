package com.robopupu.api.graph;

public interface Node<IN, OUT> extends InputNode<IN>, OutputNode<OUT> {

    /**
     * Invoked by the input {@link Node} for this {@link Node} to receive the input {@link Object}.
     * @param input The input {@link Object}.
     */
    void onInput(final IN input);

    /**
     * Invoked when the specified input {@link Node} is completed.
     * @param inputNode A completed input {@link Node}.
     *
    void onCompleted(final Node<?, OUT> inputNode);*/

    /**
     * Invoked when the input {@link Node} has detected or received an error. The default
     * implementation detaches this {@link Node} from the input {@link Node} that notified about
     * an error and dispatched the error to attached output {@link Node}s.
     * @param inputNode An input {@link Node} notifying about error.
     * @param throwable A {@link Throwable} representing the error.
     *
    void onError(final Node<?, IN> inputNode, final Throwable throwable); */

    /**
     * Attach the given output {@link Node} to the set of output {@link Node}s.
     * @param outputNode A {@link Node}.
     * @return The attached {@link Node}.
     *
    <T> Node<OUT, T> attach(final Node<OUT, T> outputNode);*/

    /**
     * Detaches the given output {@link Node} by removing from the set of output {@link Node}s.
     * @param outputNode A {@link Node}.
     * @return A {@code boolean} value indicating if the {@link Node} was removed.
     *
    boolean detach(final Node<OUT, ?> outputNode); */
}
