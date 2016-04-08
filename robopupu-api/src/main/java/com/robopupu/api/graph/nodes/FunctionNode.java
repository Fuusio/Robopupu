package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.Function;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link FunctionNode} ...
 */
public class FunctionNode<IN, OUT> extends AbstractNode<IN, OUT> {

    private final Function<IN,OUT> mFunction;

    public FunctionNode(final Function<IN,OUT> function) {
        mFunction = function;
    }

    @Override
    protected OUT processInput(final OutputNode<IN> outputNode, final IN input) {
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
