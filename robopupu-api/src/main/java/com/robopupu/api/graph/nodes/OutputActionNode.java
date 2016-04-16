package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.actions.OutputAction;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link OutputActionNode} is a {@link Node} that can be used to execute a given {@link OutputAction}
 * or arbitrary action as overridden method {@link OutputActionNode#execute()}.
 */
public class OutputActionNode<OUT> extends Node<Void, OUT> {

    private final OutputAction<OUT> mAction;

    public OutputActionNode(final OutputAction<OUT> action) {
        mAction = action;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT processInput(final OutputNode<Void> source, final Void input) {
        if (mAction != null) {
            return mAction.execute();
        } else {
            return execute();
        }
    }

    protected OUT execute() {
        return null;
    }

    @Override
    public void emitOutput() {
        processInput(null, null);
    }
}
