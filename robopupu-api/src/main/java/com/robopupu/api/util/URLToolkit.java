/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.util;

import android.webkit.URLUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class URLToolkit {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static String encode(final String format, final Charset charset, final Object... args) {
        final String data = String.format(format, args);
        try {
            return URLEncoder.encode(data, charset.name());
        } catch (UnsupportedEncodingException e) {
            L.wtf(URLToolkit.class, "encode", e.getMessage());
        }
        return null;
    }

    public static String encode(final String format, final Object... args) {
        return encode(format, DEFAULT_CHARSET, args);
    }

    public static boolean isValidUrl(final String urlString) {
        return URLUtil.isValidUrl(urlString);
    }
}