package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.functions.Function2;

/**
 * {@link Zip2Node} extends {@link ZipNode} for combining two inputs to one emitted output value.
 */
public class Zip2Node<IN1, IN2, OUT> extends ZipNode<OUT> {

    public final ZipInputNode<IN1, OUT> input1;
    public final ZipInputNode<IN2, OUT> input2;

    private final Function2<IN1, IN2, OUT> mCombineFunction;

    public Zip2Node(final Function2<IN1, IN2, OUT> combineFunction) {
        super(2);
        mCombineFunction = combineFunction;
        input1 = new ZipInputNode<>(this, 0);
        input2 = new ZipInputNode<>(this, 1);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT zip() {
        final IN1 input1 = (IN1)mBuffers[0].remove(0);
        final IN2 input2 = (IN2)mBuffers[1].remove(0);
        return mCombineFunction.eval(input1, input2);
    }
}
