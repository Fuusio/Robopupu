package com.robopupu.api.graph;

public interface OutputNode<OUT> {

    /**
     * Sets the {@link Graph} that contains this {@link OutputNode}.
     * @param graph A {@link Graph}.
     */
    void setGraph(Graph<?> graph);

    /**
     * Attaches the given target {@link InputNode} to this {@link OutputNode}.
     * @param target An {@link InputNode}.
     */
    void attach(InputNode<OUT> target);

    /**
     * Detaches the given target {@link InputNode} from this {@link OutputNode}.
     * @param target An {@link InputNode}.
     */
    void detach(InputNode<OUT> target);

    /**
     * Invoked to emit available output value(s) from this {@link OutputNode}.
     */
    void emitOutput();

    /**
     * Invoked to reset this {@link OutputNode}.
     */
    void onReset();
}
