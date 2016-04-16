package com.robopupu.api.graph.nodes;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.Node;
import com.robopupu.api.graph.OutputNode;
import com.robopupu.api.network.RequestCallback;
import com.robopupu.api.network.RequestDelegate;
import com.robopupu.api.network.RequestError;

/**
 * {@link RequestNode} extends {@link AbstractNode} to implement {@link Node} for making REST
 * requests.
 */
public class RequestNode<IN, OUT> extends AbstractNode<IN, OUT> implements RequestCallback<OUT> {

    private final RequestDelegate<OUT> mRequestDelegate;

    public RequestNode(final RequestDelegate<OUT> requestDelegate) {
        mRequestDelegate = requestDelegate;
    }

    @Override
    public void emitOutput() {
        mRequestDelegate.executeRequest(this);
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
