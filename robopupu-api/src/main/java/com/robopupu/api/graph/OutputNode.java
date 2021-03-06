package com.robopupu.api.graph;

public interface OutputNode<OUT> {

    /**
     * Gets the {@link Graph} that contains this {@link OutputNode}.
     * @return A {@link Graph}.
     */
    Graph<?> getGraph();

    /**
     * Sets the {@link Graph} that contains this {@link OutputNode}.
     * @param graph A {@link Graph}.
     */
    void setGraph(Graph<?> graph);

    /**
     * Attaches the given {@link InputNode} to this {@link OutputNode}.
     * @param inputNode An {@link InputNode}.
     * @return The attached {@link InputNode}.
     */
    InputNode<OUT> attach(InputNode<OUT> inputNode);

    /**
     * Detaches the given {@link InputNode} from this {@link OutputNode}.
     * @param inputNode An {@link InputNode}.
     */
    void detach(InputNode<OUT> inputNode);

    /**
     * Invoked to emit available output value(s) from this {@link OutputNode}.
     */
    void emitOutput();

    /**
     * Invoked to reset this {@link OutputNode}.
     */
    void onReset();
}
