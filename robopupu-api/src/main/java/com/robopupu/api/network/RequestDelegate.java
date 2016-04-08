package com.robopupu.api.network;

/**
 * {@link RequestDelegate} defines an interface that can be used to execute a request.
 */
public interface RequestDelegate<T_Response> {

    void executeRequest(RequestCallback<T_Response> callback);
}
