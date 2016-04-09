package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.functions.Function2;

/**
 * {@link Zip2Node} extends {@link ZipNode} for combining two inputs to one emitted output value.
 */
public class Zip2Node<IN1, IN2, OUT> extends ZipNode<OUT> {

    public final ZipInputNode<IN1, OUT> in1;
    public final ZipInputNode<IN2, OUT> in2;

    private final Function2<IN1, IN2, OUT> mCombineFunction;

    public Zip2Node(final Function2<IN1, IN2, OUT> combineFunction) {
        super(2);
        mCombineFunction = combineFunction;
        in1 = new ZipInputNode<>(this, 0);
        in2 = new ZipInputNode<>(this, 1);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT zip() {
        final IN1 in1 = (IN1)mBuffers[0].remove(0);
        final IN2 in2 = (IN2)mBuffers[1].remove(0);
        return mCombineFunction.eval(in1, in2);
    }
}
