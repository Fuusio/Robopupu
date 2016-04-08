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

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.robopupu.api.network.RequestCallback;

import java.io.UnsupportedEncodingException;

public class GsonRequest<T> extends BaseRequest<T> {

    private final static String TAG = GsonRequest.class.getSimpleName();

    private final Gson mGson;
    private final Class<T> mResponseClass;

    private String mRequestBody;

    public GsonRequest(final Class<T> responseClass) {
        this(Method.GET, null, responseClass, null);
    }

    public GsonRequest(final Class<T> responseClass, final Object body) {
        this(Method.GET, body, responseClass, null);
    }

    public GsonRequest(final Class<T> responseClass, final RequestCallback<T> requestCallback) {
        this(Method.GET, null, responseClass, requestCallback);
    }

    public GsonRequest(final Class<T> responseClass, final Object body, final RequestCallback<T> requestCallback) {
        this(Method.GET, body, responseClass, requestCallback);
    }

    public GsonRequest(final int method, final Object body, final Class<T> responseClass, final RequestCallback<T> requestCallback) {
        super(method, requestCallback);

        mGson = createGson();
        mResponseClass = responseClass;

        if (body != null) {
            mRequestBody = mGson.toJson(body);
        } else {
            mRequestBody = null;
        }
    }

    protected Gson createGson() {
        return new Gson();
    }

    @Override
    public byte[] getBody() {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "getBody() : " + e.getMessage());
            VolleyLog.wtf("Unsupported encoding while trying to get the bytes of %s using %s", mRequestBody, PROTOCOL_CHARSET);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void setBody(final Object body) {
        mRequestBody = (body != null) ? mGson.toJson(body) : null;
    }

    @Override
    protected Response<T> parseNetworkResponse(final NetworkResponse response) {
        try {
            final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(jsonString, mResponseClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (final UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
