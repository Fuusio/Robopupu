package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.Function;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link FunctionNode} takes a {@link Function} to produce an output of value of the specified
 * type from the received input value of specified type.
 */
public class FunctionNode<IN, OUT> extends Node<IN, OUT> {

    private final Function<IN,OUT> mFunction;

    public FunctionNode(final Function<IN,OUT> function) {
        mFunction = function;
    }

    @Override
    protected OUT processInput(final OutputNode<IN> source, final IN input) {
        if (mFunction != null) {
            return mFunction.eval(input);
        } else {
            return eval(input);
        }
    }

    protected OUT eval(final IN input) {
        return null;
    }
}
