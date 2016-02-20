/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://fuusio.org
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

import com.android.volley.VolleyError;

import org.fuusio.api.network.HttpHeaders;
import org.fuusio.api.network.RequestError;

public class VolleyRequestError extends RequestError {

    private final HttpHeaders mHeaders;

    public VolleyRequestError(final VolleyError error) {
        mException = error.getCause();
        mMessage = error.getMessage();
        mNetworkTime = error.getNetworkTimeMs();
        mStatusCode = error.networkResponse.statusCode;
        mHeaders = new HttpHeaders(error.networkResponse.headers);
    }

    public final HttpHeaders getHeaders() {
        return mHeaders;
    }

}
