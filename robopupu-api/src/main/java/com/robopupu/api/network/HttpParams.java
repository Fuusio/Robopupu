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

import com.robopupu.api.util.KeyValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpParams {

    public static final Charset DEFAULT_ENCODING = Charset.defaultCharset();

    private final List<KeyValue<String, String>> keyValues;
    private final Charset paramsEncoding;

    public HttpParams() {
        this(DEFAULT_ENCODING);
    }

    public HttpParams(final Charset paramsEncoding) {
        keyValues = new ArrayList<>();
        this.paramsEncoding = paramsEncoding;
    }

    public List<KeyValue<String, String>> getKeyValues() {
        return keyValues;
    }

    public HttpParams add(final String key, final String value) {
        keyValues.add(new KeyValue<>(key, value));
        return this;
    }

    public void addAll(final HttpParams params) {
        if (params != null) {
            keyValues.addAll(params.keyValues);
        }
    }

    public void clear() {
        keyValues.clear();
    }

    public Map<String, String> getMap() {
        final Map<String, String> map = new HashMap<>();

        for (final KeyValue<String, String> keyValue : keyValues) {
            map.put(keyValue.getKey(), keyValue.getValue());
        }

        return map;
    }

    public final int getSize() {
        return keyValues.size();
    }

    public void encodeParameters(final StringBuilder encodedParams) {
        encodeParameters(encodedParams, paramsEncoding);
    }

    public void encodeParameters(final StringBuilder encodedParams, final Charset paramsEncoding) {
        final String encoding = paramsEncoding.name();
        boolean firstParameter = true;

        try {
            for (final KeyValue<String, String> keyValue : keyValues) {
                final String key = URLEncoder.encode(keyValue.getKey(), encoding);
                final String value = URLEncoder.encode(keyValue.getValue(), encoding);

                if (!firstParameter) {
                    encodedParams.append('&');
                } else {
                    firstParameter = false;
                }

                encodedParams.append(key);
                encodedParams.append('=');
                encodedParams.append(value);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, e);
        }
    }

    /**
     * Tests if this {@link HttpParams} contains any key values.
     * @return A {@code boolean} value.
     */
    public boolean hasValues() {
        return keyValues.size() > 0;
    }
}
