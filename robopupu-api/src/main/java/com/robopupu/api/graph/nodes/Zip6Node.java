package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.functions.Function6;

/**
 * {@link Zip6Node} extends {@link ZipNode} for combining two inputs to one emitted output value.
 */
public class Zip6Node<IN1, IN2, IN3, IN4, IN5, IN6, OUT> extends ZipNode<OUT> {

    public final ZipInputNode<IN1, OUT> in1;
    public final ZipInputNode<IN2, OUT> in2;
    public final ZipInputNode<IN3, OUT> in3;
    public final ZipInputNode<IN4, OUT> in4;
    public final ZipInputNode<IN5, OUT> in5;
    public final ZipInputNode<IN6, OUT> in6;

    private final Function6<IN1, IN2, IN3, IN4, IN5, IN6, OUT> mCombineFunction;

    public Zip6Node(final Function6<IN1, IN2, IN3, IN4, IN5, IN6, OUT> combineFunction) {
        super(6);
        mCombineFunction = combineFunction;
        in1 = new ZipInputNode<>(this, 0);
        in2 = new ZipInputNode<>(this, 1);
        in3 = new ZipInputNode<>(this, 2);
        in4 = new ZipInputNode<>(this, 3);
        in5 = new ZipInputNode<>(this, 4);
        in6 = new ZipInputNode<>(this, 5);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OUT zip() {
        final IN1 in1 = (IN1)mBuffers[0].remove(0);
        final IN2 in2 = (IN2)mBuffers[1].remove(0);
        final IN3 in3 = (IN3)mBuffers[2].remove(0);
        final IN4 in4 = (IN4)mBuffers[3].remove(0);
        final IN5 in5 = (IN5)mBuffers[4].remove(0);
        final IN6 in6 = (IN6)mBuffers[5].remove(0);
        return mCombineFunction.eval(in1, in2, in3, in4, in5, in6);
    }
}
