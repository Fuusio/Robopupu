package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.functions.Function3;

/**
 * {@link Zip3Node} extends {@link ZipNode} for combining three inputs to one emitted output value.
 */
public class Zip3Node<IN1, IN2, IN3, OUT> extends ZipNode<OUT> {

    public final ZipInputNode<IN1, OUT> in1;
    public final ZipInputNode<IN2, OUT> in2;
    public final ZipInputNode<IN3, OUT> in3;

    private final Function3<IN1, IN2, IN3, OUT> mCombineFunction;

    public Zip3Node(final Function3<IN1, IN2, IN3, OUT> combineFunction) {
        super(3);
        mCombineFunction = combineFunction;
        in1 = new ZipInputNode<>(this, 0);
        in2 = new ZipInputNode<>(this, 1);
        in3 = new ZipInputNode<>(this, 2);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT zip() {
        final IN1 input1 = (IN1)mBuffers[0].remove(0);
        final IN2 input2 = (IN2)mBuffers[1].remove(0);
        final IN3 input3 = (IN3)mBuffers[2].remove(0);
        return mCombineFunction.eval(input1, input2, input3);
    }
}
