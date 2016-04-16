package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.graph.functions.BooleanFunction;

/**
 * {@link FirstNode} emits only the first received input value if no condition is given as
 * a {@link BooleanFunction}. If condition is given, then the first value that gets accepted by
 * the condition is emitted.
 */
public class FirstNode<IN> extends Node<IN, IN> {

    private BooleanFunction<IN> mCondition;
    private int mCounter;
    private boolean mValueEmitted;

    public FirstNode() {
        this(null);
    }

    public FirstNode(final BooleanFunction<IN> condition) {
        setCondition(condition);
        mCounter = 0;
        mValueEmitted = false;
    }

    public void setCondition(final BooleanFunction condition) {
        mCondition = condition;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null && !mValueEmitted) {
            if (mCondition != null) {
                if (mCondition.eval(input)) {
                    mValueEmitted = true;
                    return input;
                }
            } else if (mCounter == 0) {
                mValueEmitted = true;
                return input;
            }
        }
        mCounter++;
        return null;
    }

    @Override
    protected void doOnReset() {
        mValueEmitted = false;
    }
}
