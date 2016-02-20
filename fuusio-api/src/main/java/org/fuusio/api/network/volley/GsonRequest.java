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

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.fuusio.api.util.L;

import java.io.UnsupportedEncodingException;

public class GsonRequest<T> extends VolleyRequest<T> {

    private final Gson mGson;
    private final Class<T> mResponseClass;

    private String mRequestBody;

    public GsonRequest(final String url, final Class responseClass,
                       final Response.Listener listener, final Response.ErrorListener errorListener) {
        this(Method.GET, url, null, responseClass, listener, errorListener);
    }

    public GsonRequest(final int pMethod, final String url, final Class responseClass,
                       final Response.Listener listener, final Response.ErrorListener errorListener) {
        this(pMethod, url, null, responseClass, listener, errorListener);
    }

    public GsonRequest(final int pMethod, final String url, final Object body, final Class responseClass,
                       final Response.Listener listener, final Response.ErrorListener errorListener) {
        super(pMethod, url, listener, errorListener);

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
            L.wtf(this, "getBody", e);
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    @Override
    public void setBody(final Object body) {
        mRequestBody = (body != null) ? mGson.toJson(body) : null;
    }

    @Override
    protected Response parseNetworkResponse(final NetworkResponse response) {
        try {
            final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(jsonString, mResponseClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (final UnsupportedEncodingException pException) {
            return Response.error(new ParseError(pException));
        }
    }
}
