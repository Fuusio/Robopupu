package com.robopupu.api.graph;

public interface OutputNode<OUT> {

    /**
     * Attaches the given {@link InputNode} to this {@link OutputNode}.
     * @param inputNode An{@link InputNode}.
     */
    void attach(InputNode<OUT> inputNode);

    /**
     * Detaches the given {@link InputNode} from this {@link OutputNode}.
     * @param inputNode An {@link InputNode}.
     */
    void detach(InputNode<OUT> inputNode);

    /**
     * Invoked to emit available output value(s) from this {@link OutputNode}.
     */
    void emit();

    /**
     * Invoked to reset this {@link OutputNode}.
     */
    void onReset();
}
