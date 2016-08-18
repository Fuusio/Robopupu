package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.graph.actions.Action;

/**
 * {@link ActionNode} is a {@link Node} that can be used to execute a given {@link Action}
 * or an arbitrary action defined as overridden method {@link Action#execute()}.
 */
public class ActionNode<IN, OUT> extends Node<IN, OUT> {

    private final Action action;

    public ActionNode(final Action action) {
        this.action = action;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT processInput(final OutputNode<IN> source, final IN input) {
        if (action != null) {
            action.execute();
        } else {
            execute();
        }
        return (OUT)input;
    }

    protected void execute() {
    }
}
