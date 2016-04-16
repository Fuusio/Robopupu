package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Action1;
import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link Action1Node} is a {@link Node} that can be used to execute a given {@link Action1}
 * or arbitrary action as overridden method {@link Action1#execute(Object)}.
 */
public class Action1Node<IN, OUT> extends Node<IN, OUT> {

    private final Action1<IN> mAction;

    public Action1Node(final Action1<IN> action) {
        mAction = action;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT processInput(final OutputNode<IN> source, final IN input) {
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
