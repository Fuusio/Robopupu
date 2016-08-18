package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.network.RequestCallback;
import com.robopupu.api.network.RequestDelegate;
import com.robopupu.api.network.RequestError;

/**
 * {@link RequestNode} implements a {@link Node} that can be used to execute REST
 * requests using {@link RequestDelegate} interface.
 */
public class RequestNode<IN, OUT> extends Node<IN, OUT> implements RequestCallback<OUT> {

    private final RequestDelegate<OUT> requestDelegate;

    public RequestNode(final RequestDelegate<OUT> requestDelegate) {
        this.requestDelegate = requestDelegate;
    }

    @Override
    public void emitOutput() {
        requestDelegate.executeRequest(this);
    }

    @Override
    protected OUT processInput(final OutputNode<IN> source, final IN input) {
        emitOutput();
        return null;
    }

    @Override
    public void onResponse(final OUT response) {
        emitOutput(response);
    }

    @Override
    public void onError(final RequestError requestError) {
        dispatchError(this, requestError);
    }
}
