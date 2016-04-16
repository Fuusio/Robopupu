package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.graph.functions.BooleanFunction;

import java.util.ArrayList;

/**
 * {@link LastNode} emits only the last received input value if no condition is given as
 * a {@link BooleanFunction}. If condition is given, then the last value that gets accepted by
 * the condition is emitted.
 */
public class LastNode<IN> extends Node<IN, IN> {

    private ArrayList<IN> mBuffer;
    private BooleanFunction<IN> mCondition;

    public LastNode() {
        this(null);
    }

    public LastNode(final BooleanFunction<IN> condition) {
        mBuffer = new ArrayList<>();
        setCondition(condition);
    }

    public void setCondition(final BooleanFunction condition) {
        mCondition = condition;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null) {
            mBuffer.add(input);
        }
        return null;
    }

    @Override
    public void onCompleted(final OutputNode<?> source) {
        final int size = mBuffer.size();

        if (size > 0) {
            if (mCondition != null) {
                for (int i = size - 1; i >= 0; i--) {
                    final IN value = mBuffer.get(i);

                    if (mCondition.eval(value)) {
                        emitOutput(value);
                        break;
                    }
                }
            } else {
                emitOutput(mBuffer.get(size - 1 ));
            }
        }
        completed(this);
    }
}
