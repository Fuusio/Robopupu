package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.graph.actions.Action;
import com.robopupu.api.graph.actions.Action1;

/**
 * {@link ActionNode} is a {@link Node} that can be used to execute a given {@link Action}
 * or arbitrary action as overridden method {@link Action#execute()}.
 */
public class ActionNode<IN, OUT> extends Node<IN, OUT> {

    private final Action mAction;

    public ActionNode(final Action action) {
        mAction = action;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT processInput(final OutputNode<IN> source, final IN input) {
        if (mAction != null) {
            mAction.execute();
        } else {
            execute();
        }
        return (OUT)input;
    }

    protected void execute() {
    }
}
