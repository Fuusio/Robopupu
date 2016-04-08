package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.Action;
import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ActionNode} is a {@link Node} that can be used to execute arbitrary action
 * in method {@link ActionNode#execute(Object)}.
 */
public class ActionNode<IN, OUT> extends AbstractNode<IN, OUT> {

    private final Action<IN> mAction;

    public ActionNode(final Action<IN> action) {
        mAction = action;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT processInput(final OutputNode<IN> outputNode, final IN input) {
        if (mAction != null) {
            mAction.execute(input);
        } else {
            execute(input);
        }
        return (OUT)input;
    }

    protected void execute(final IN input) {
    }
}
