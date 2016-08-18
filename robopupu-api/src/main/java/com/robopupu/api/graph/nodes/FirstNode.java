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

    private BooleanFunction<IN> condition;
    private int counter;
    private boolean valueEmitted;

    public FirstNode() {
        this(null);
    }

    public FirstNode(final BooleanFunction<IN> condition) {
        setCondition(condition);
        counter = 0;
        valueEmitted = false;
    }

    public void setCondition(final BooleanFunction condition) {
        this.condition = condition;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> source, final IN input) {
        if (input != null && !valueEmitted) {
            if (condition != null) {
                if (condition.eval(input)) {
                    valueEmitted = true;
                    return input;
                }
            } else if (counter == 0) {
                valueEmitted = true;
                return input;
            }
        }
        counter++;
        return null;
    }

    @Override
    protected void doOnReset() {
        valueEmitted = false;
    }
}
