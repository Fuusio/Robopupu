/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://fuusio.org
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
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;

public class JsonRequest extends VolleyRequest<JsonElement> {

    public JsonRequest(final String url, final Listener<JsonElement> responseListener, final ErrorListener errorListener) {
        super(url, responseListener, errorListener);
    }

    public JsonRequest(final int pMethod, String url, final Listener<JsonElement> responseListener, final ErrorListener errorListener) {
        super(pMethod, url, responseListener, errorListener);
    }

    @Override
    protected Response<JsonElement> parseNetworkResponse(final NetworkResponse response) {
        try {
            final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            final JsonElement jsonElement = new JsonParser().parse(jsonString);
            return Response.success(jsonElement, HttpHeaderParser.parseCacheHeaders(response));
        } catch (final UnsupportedEncodingException pException) {
            return Response.error(new ParseError(pException));
        }
    }

    @Override
    public void setBody(final Object body) {
        // Do nothing
    }
}
