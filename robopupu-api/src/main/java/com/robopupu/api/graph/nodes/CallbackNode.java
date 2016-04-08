package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.Graph;
import com.robopupu.api.graph.OutputNode;

import java.util.ArrayList;

public class CallbackNode<IN> extends AbstractNode<IN, IN> {

    private Graph.Callback<IN> mCallback;

    public CallbackNode(final Graph.Callback<IN> callback) {
        mCallback = callback;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IN processInput(final OutputNode<IN> outputNode, final IN input) {
        mCallback.onInput(input);
        return input;
    }

    @Override
    public void onCompleted(final OutputNode<?> outputNode) {
        mCallback.onCompleted();
    }

    @Override
    public void onError(final OutputNode<?> outputNode, final Throwable throwable) {
        mCallback.onError(throwable);
    }
}
