package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.actions.OutputAction;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link OutputActionNode} is a {@link Node} that can be used to execute a given {@link OutputAction}
 * or arbitrary action defined as overridden method {@link OutputActionNode#execute()}.
 */
public class OutputActionNode<OUT> extends Node<Void, OUT> {

    private final OutputAction<OUT> action;

    public OutputActionNode(final OutputAction<OUT> action) {
        this.action = action;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT processInput(final OutputNode<Void> source, final Void input) {
        if (action != null) {
            return action.execute();
        } else {
            return execute();
        }
    }

    protected OUT execute() {
        return null;
    }

    @Override
    public void emitOutput() {
        emitOutput(processInput(null, null));
    }
}
