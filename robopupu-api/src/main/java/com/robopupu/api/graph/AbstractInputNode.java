package com.robopupu.api.graph;

/**
 * {@link AbstractInputNode} provide an abstract base class for implementing {@link InputNode}s.
 * @param <IN> The input type.
 */
public abstract class AbstractInputNode<IN> implements InputNode<IN> {

    /**
     * Constructs a new instance of {@link AbstractInputNode}.
     */
    protected AbstractInputNode() {
    }

    @Override
    public void onInput(final OutputNode<IN> outputNode, final IN input) {
        processInput(outputNode, input);
    }

    /**
     * Invoked by {@link InputNode#onInput(OutputNode, Object)} when the given input {@link Object}
     * has been received from the {@link OutputNode} that emitted it.
     * @param outputNode An {@link OutputNode} that emitted the input.
     * @param input The input {@link Object}.
     */
    protected abstract void processInput(final OutputNode<IN> outputNode, final IN input);

    @Override
    public void onCompleted(final OutputNode<?> outputNode) {
        // By default do nothing
    }

    @Override
    public void onError(final OutputNode<?> outputNode, final Throwable throwable) {
        // By default do nothing
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
