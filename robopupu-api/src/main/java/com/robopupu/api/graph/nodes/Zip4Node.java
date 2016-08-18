package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.functions.Function4;

/**
 * {@link Zip4Node} extends {@link ZipNode} for combining two inputs to one emitted output value.
 */
public class Zip4Node<IN1, IN2, IN3, IN4, OUT> extends ZipNode<OUT> {

    public final ZipInputNode<IN1, OUT> in1;
    public final ZipInputNode<IN2, OUT> in2;
    public final ZipInputNode<IN3, OUT> in3;
    public final ZipInputNode<IN4, OUT> in4;

    private final Function4<IN1, IN2, IN3, IN4, OUT> mCombineFunction;

    public Zip4Node(final Function4<IN1, IN2, IN3, IN4, OUT> combineFunction) {
        super(4);
        mCombineFunction = combineFunction;
        in1 = new ZipInputNode<>(this, 0);
        in2 = new ZipInputNode<>(this, 1);
        in3 = new ZipInputNode<>(this, 2);
        in4 = new ZipInputNode<>(this, 3);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT zip() {
        final IN1 in1 = (IN1) buffers[0].remove(0);
        final IN2 in2 = (IN2) buffers[1].remove(0);
        final IN3 in3 = (IN3) buffers[2].remove(0);
        final IN4 in4 = (IN4) buffers[3].remove(0);
        return mCombineFunction.eval(in1, in2, in3, in4);
    }
}
