package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.functions.Function8;

/**
 * {@link Zip8Node} extends {@link ZipNode} for combining two inputs to one emitted output value.
 */
public class Zip8Node<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, OUT> extends ZipNode<OUT> {

    public final ZipInputNode<IN1, OUT> in1;
    public final ZipInputNode<IN2, OUT> in2;
    public final ZipInputNode<IN3, OUT> in3;
    public final ZipInputNode<IN4, OUT> in4;
    public final ZipInputNode<IN5, OUT> in5;
    public final ZipInputNode<IN6, OUT> in6;
    public final ZipInputNode<IN7, OUT> in7;
    public final ZipInputNode<IN8, OUT> in8;

    private final Function8<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, OUT> mCombineFunction;

    public Zip8Node(final Function8<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, OUT> combineFunction) {
        super(8);
        mCombineFunction = combineFunction;
        in1 = new ZipInputNode<>(this, 0);
        in2 = new ZipInputNode<>(this, 1);
        in3 = new ZipInputNode<>(this, 2);
        in4 = new ZipInputNode<>(this, 3);
        in5 = new ZipInputNode<>(this, 4);
        in6 = new ZipInputNode<>(this, 5);
        in7 = new ZipInputNode<>(this, 6);
        in8 = new ZipInputNode<>(this, 7);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT zip() {
        final IN1 in1 = (IN1) buffers[0].remove(0);
        final IN2 in2 = (IN2) buffers[1].remove(0);
        final IN3 in3 = (IN3) buffers[2].remove(0);
        final IN4 in4 = (IN4) buffers[3].remove(0);
        final IN5 in5 = (IN5) buffers[4].remove(0);
        final IN6 in6 = (IN6) buffers[5].remove(0);
        final IN7 in7 = (IN7) buffers[6].remove(0);
        final IN8 in8 = (IN8) buffers[7].remove(0);
        return mCombineFunction.eval(in1, in2, in3, in4, in5, in6, in7, in8);
    }
}
