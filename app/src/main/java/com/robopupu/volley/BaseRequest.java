/*
 * Copyright (C) 2014 - 2015 Marko Salmela.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robopupu.volley;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.robopupu.api.network.HttpHeaders;
import com.robopupu.api.network.RequestCallback;
import com.robopupu.api.network.RequestError;

import java.util.Map;

/**
 * {@link BaseRequest} provides an abstract base class for implementing Volley {@link Request}s.
 *
 * @param <T_Response> The parametrized type of the response object.
 */
public abstract class BaseRequest<T_Response> extends Request<T_Response> {

    protected static final String PROTOCOL_CHARSET = "utf-8";

    private boolean isBuild;
    private HttpHeaders headers;
    private ErrorListener errorListener;
    private Listener<T_Response> responseListener;
    private String url;

    protected BaseRequest() {
        this(Method.GET);
    }

    protected BaseRequest(final int method) {
        this(method, null, null);
    }

    protected BaseRequest(final RequestCallback<T_Response> requestCallback) {
        this(Method.GET, createResponseListener(requestCallback), createErrorListener(requestCallback));
    }

    protected BaseRequest(final int method, final RequestCallback<T_Response> requestCallback) {
        this(method, createResponseListener(requestCallback), createErrorListener(requestCallback));
    }

    protected BaseRequest(final Listener<T_Response> responseListener, final ErrorListener errorListener) {
        this(Method.GET, responseListener, errorListener);
    }

    protected BaseRequest(final int method, final Listener<T_Response> responseListener, final ErrorListener errorListener) {
        super(method, null, errorListener);
        this.responseListener = responseListener;
    }

    /**
     * Sets the {@link RequestCallback} for this {@link BaseRequest}.
     * @param callback A {@link RequestCallback}.
     */
    public void setCallback(final RequestCallback<T_Response> callback) {
        setResponseListener(createResponseListener(callback));
        setErrorListener(createErrorListener(callback));
    }

    /**
     * Creates an {@link ErrorListener} from the given {@link RequestCallback}.
     * @param requestCallback A {@link RequestCallback}. May be {@code null},
     * @return An {@link ErrorListener}. May return {@code null}.
     */
    private static <T_Response> ErrorListener createErrorListener(final RequestCallback<T_Response> requestCallback) {
        if (requestCallback != null) {
            return volleyError -> requestCallback.onError(createRequestError(volleyError));
        }
        return null;
    }

    public static RequestError createRequestError(final VolleyError volleyError) {
        final RequestError requestError = new RequestError();
        requestError.setCause(volleyError.getCause());
        requestError.setHeaders(volleyError.networkResponse.headers);
        requestError.setMessage(volleyError.getMessage());
        requestError.setNetworkTime(volleyError.getNetworkTimeMs());
        requestError.setStatusCode(volleyError.networkResponse.statusCode);
        return requestError;
    }

    /**
     * Creates an {@link Listener} from the given {@link RequestCallback}.
     * @param requestCallback A {@link RequestCallback}. May be {@code null},
     * @return An {@link Listener}. May return {@code null}.
     */
    private static <T_Response> Listener<T_Response> createResponseListener(final RequestCallback<T_Response> requestCallback) {
        if (requestCallback != null) {
            return requestCallback::onResponse;
        }
        return null;
    }

    /**
     * Tests if the set {@link BaseRequest} has built for execution.
     * @return A {@code boolean} value.
     */
    public boolean isBuild() {
        return isBuild;
    }

    /**
     * Sets this {@link BaseRequest} to be build depending on the given {@code boolean} value.
     * @param build A {@code boolean} value.
     */
    public void setBuild(final boolean build) {
        isBuild = build;
    }

    /**
     * Sets the URL.
     * @param url An url as a {@link String}. May not be {@code null}.
     */
    public void setUrl(@NonNull final String url) {
        this.url = url;
    }

    @Override
    public ErrorListener getErrorListener() {
        return errorListener;
    }

    /**
     * Sets the {@link ErrorListener}.
     * @param errorListener A {@link ErrorListener}.
     */
    public void setErrorListener(final ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    /**
     * Gets the response {@link Listener}.
     * @return A {@link Listener}.
     */
    public Listener<T_Response> getResponseListener() {
        return responseListener;
    }

    /**
     * Sets the response {@link Listener}.
     * @param listener A {@link Listener}.
     */
    public void setResponseListener(final Listener<T_Response> listener) {
        responseListener = listener;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return (headers != null) ? headers.getMap() : super.getHeaders();
    }

    /**
     * Set the headers for this {@link BaseRequest}.
     * @param headers A {@link HttpHeaders} containing the headers.
     */
    public void setHeaders(final HttpHeaders headers) {
        this.headers = headers;
    }

    @Override
    protected abstract Response<T_Response> parseNetworkResponse(NetworkResponse response);

    @Override
    public void deliverError(final VolleyError error) {
        if (errorListener != null) {
            errorListener.onErrorResponse(error);
        }
    }

    @Override
    protected void deliverResponse(final T_Response response) {
        if (responseListener != null) {
            responseListener.onResponse(response);
        }
    }

    public abstract void setBody(Object body);
}