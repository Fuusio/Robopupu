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
package com.robopupu.api.network;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private final Map<String, String> headers;

    public HttpHeaders() {
        headers = new HashMap<>();
    }

    public HttpHeaders(final Map<String, String> headers) {
        this.headers = new HashMap<>(headers);
    }

    public final HttpHeaders add(final HeaderRequestField field, final String value) {
        return add(field.getName(), value);
    }

    public final HttpHeaders add(final String field, final String value) {
        headers.put(field, value);
        return this;
    }

    public final void addAll(final HttpHeaders headers) {
        if (headers != null) {
            final Map<String, String> map = headers.getMap();

            for (final String key : map.keySet()) {
                this.headers.put(key, map.get(key));
            }
        }
    }

    public final Map<String, String> getMap() {
        return headers;
    }

    public void clear() {
        headers.clear();
    }

    public boolean isEmpty() {
        return headers.isEmpty();
    }
}
