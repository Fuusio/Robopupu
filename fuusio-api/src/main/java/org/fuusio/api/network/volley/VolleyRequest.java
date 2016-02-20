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
package org.fuusio.api.network.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.fuusio.api.network.HttpHeaders;

import java.util.Map;

/**
 * {@link VolleyRequest} provides an abstract base class for implementing Volley {@link Request}s
 * to provide additional framework support for using Volley to implement REST communication.
 *
 * @param <T_Response> The parametrized type of the response object.
 */
public abstract class VolleyRequest<T_Response> extends Request<T_Response> {

    protected static final String PROTOCOL_CHARSET = "utf-8";

    protected HttpHeaders mHeaders;
    protected Response.Listener<T_Response> mResponseListener;

    protected VolleyRequest(final String url, final Listener<T_Response> responseListener, final ErrorListener errorListener) {
        this(Method.GET, url, responseListener, errorListener);
    }

    protected VolleyRequest(final int method, String url, final Listener<T_Response> responseListener, final ErrorListener errorListener) {
        super(method, url, errorListener);
        mResponseListener = responseListener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return (mHeaders != null) ? mHeaders.getMap() : super.getHeaders();
    }

    /**
     * Set the headers for this {@link VolleyRequest}.
     * @param headers A {@link HttpHeaders} containing the headers.
     */
    public void setHeaders(final HttpHeaders headers) {
        mHeaders.addAll(headers);
    }

    @Override
    protected abstract Response<T_Response> parseNetworkResponse(NetworkResponse response);

    @Override
    protected void deliverResponse(final T_Response response) {
        if (mResponseListener != null) {
            mResponseListener.onResponse(response);
        }
    }

    public abstract void setBody(Object body);
}
