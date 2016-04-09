package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.InputNode;
import com.robopupu.api.graph.OutputNode;

/**
 * {@link ConcatNode} sums the reveived input values until {@link InputNode#onCompleted(OutputNode)}
 * is invoked.
 */
public class ConcatNode<IN> extends AbstractNode<IN, String> {

    private final StringBuilder mString;

    public ConcatNode() {
        mString = new StringBuilder();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String processInput(final OutputNode<IN> outputNode, final IN input) {
        if (input != null) {
            mString.append(input.toString());
        } else {
            error(this, new ClassCastException(createErrorMessage("Received an input value that cannot be converted to String")));
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> outputNode) {
        out(mString.toString());
    }
}
