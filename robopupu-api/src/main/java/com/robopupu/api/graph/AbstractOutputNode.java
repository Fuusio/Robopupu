package com.robopupu.api.graph;

import java.util.ArrayList;

/**
 * {@link AbstractOutputNode} provide an abstract base class for implementing {@link OutputNode}s.
 * @param <OUT> The output type.
 */
public abstract class AbstractOutputNode<OUT> implements OutputNode<OUT> {

    protected final ArrayList<InputNode<OUT>> mInputNodes;

    /**
     * Constructs a new instance of {@link AbstractOutputNode}.
     */
    protected AbstractOutputNode() {
        mInputNodes = new ArrayList<>();
    }

    @Override
    public void emit() {
        // By default do nothing
    }

    /**
     * Invoked to emit the given output to attached {@link InputNode}s, if any.
     * @param output The outbut {@link Object}.
     */
    protected void out(final OUT output) {
        if (output != null) {
            for (final InputNode<OUT> inputNode : mInputNodes) {
                inputNode.onInput(this, output);
            }
        }
    }

    /**
     * Invoked to notify all {@link InputNode}s about completion.
     * @param outputNode The completed {@link OutputNode}.
     */
    protected void completed(final OutputNode<?> outputNode) {
        for (final InputNode<OUT> inputNode : mInputNodes) {
            inputNode.onCompleted(outputNode);
        }
    }

    /**
     * Dispatches an error to all output {@link InputNode}s, if any.
     * @param outputNode The {@link OutputNode} that has detected the error.
     * @param throwable A {@link Throwable} representing the error.
     */
    protected void error(final OutputNode<?> outputNode, final Throwable throwable) {
        for (final InputNode<OUT> inputNode : mInputNodes) {
            inputNode.onError(outputNode, throwable);
        }
    }

    @Override
    public void attach(final InputNode<OUT> inputNode) {
        addInputNode(inputNode);
    }

    /**
     * Adds the given {@link InputNode} to the set of attached {@link InputNode}s.
     * @param inputNode A {@link InputNode}.
     */
    protected void addInputNode(final InputNode<OUT> inputNode) {
        if (!mInputNodes.contains(inputNode)) {
            mInputNodes.add(inputNode);
            onAttached(inputNode);
        }
    }

    /**
     * Invoked when the given output {@link InputNode} has been attached to this
     * {@link AbstractOutputNode}.
     * @param inputNode The attached {@link InputNode}.
     */
    protected void onAttached(final InputNode<OUT> inputNode) {
        // By default do nothing
    }

    @Override
    public void detach(final InputNode<OUT> inputNode) {
        removeInputNode(inputNode);
    }

    /**
     * Removes the given {@link InputNode} from the set of attached {@link InputNode}s.
     * @param inputNode A {@link InputNode}.
     */
    protected void removeInputNode(final InputNode<OUT> inputNode) {
        if (mInputNodes.remove(inputNode)) {
            onDetached(inputNode);
        }
    }

    /**
     * Invoked when the given  {@link InputNode} has been detached from this
     * {@link AbstractOutputNode}.
     * @param inputNode The detached {@link InputNode}.
     */
    @SuppressWarnings("unused")
    protected void onDetached(final InputNode<OUT> inputNode) {
        // By default do nothing
    }

    /**
     * Tests if this {@link OutputNode} has any attached @link InputNode}s.
     * @return A {@code boolean} value.
     */
    public boolean hasInputNodes() {
        return !mInputNodes.isEmpty();
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
}
