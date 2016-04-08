package com.robopupu.api.graph;

public interface OutputNode<OUT> {

    /**
     * Invoked to emit available output value(s) from this {@link OutputNode}.
     */
    void emit();

    /**
     * Attaches the given {@link InputNode} to this {@link OutputNode}.
     * @param inputNode An{@link InputNode}.
     */
    void attach(final InputNode<OUT> inputNode);

    /**
     * Detaches the given {@link InputNode} from this {@link OutputNode}.
     * @param inputNode An {@link InputNode}.
     */
    void detach(final InputNode<OUT> inputNode);
}
