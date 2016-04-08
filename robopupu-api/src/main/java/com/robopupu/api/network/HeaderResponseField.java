/*
 * Copyright (C) 2012 Marko Salmela.
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

public enum HeaderResponseField {

    ACCEPT_CONTROL_ALLOW_ORIGIN("Accept-Control-Allow-Origin"),
    ACCEPT_PATCH("Accept-Patch"),
    ACCEPT_RANGES("Accept-Ranges"),
    AGE("Age"),
    ALLOW("Allow"),
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    CONTENT_ENCODING("Content-Encoding"),
    CONTENT_LANGUAGE("Content-Language"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_LOCATION("Content-Location"),
    CONTENT_MD5("Content-MD5"),
    CONTENT_DISPOSITION("Content-Disposition"),
    CONTENT_RANGE("Content-Range"),
    CONTENT_TYPE("Content-Type"),
    DATE("Date"),
    ETAG("ETag"),
    EXPIRES("Expires"),
    HOST("Host"),
    LAST_MODIFIED("Last-Modified"),
    LINK("Link"),
    LOCATION("Location"),
    P3P("P3P"),
    PRAGMA("Pragma"),
    PROXY_AUTHENTICATE("Proxy-Authenticate"),
    REFRESH("Refresh"),
    RETRY_AFTER("Retry-After"),
    SERVER("Server"),
    SET_COOKIE("Set-Cookie"),
    STATUS("Status"),
    STRICT_TRANSPORT_SECURITY("Strict-Transport-Security"),
    TRAILER("Trailer"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    UPGRADE("Upgrade"),
    VARY("Vary"),
    VIA("Via"),
    WARNING("Warning"),
    WWW_AUTHENTICATE("WWW-Authenticate"),
    X_FRAME_OPTIONS("X-Frame-Options"),

    // Common non-standard response fields

    PUBLIC_KEY_PINS("Public-Key-Pins"),
    X_XSS_PROTECTION("X-XSS-Protection"),
    X_CONTENT_SECURITY_POLICY("X-Content-Security-Policy"),
    X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options"),
    X_POWERED_BY("X-Powered-By"),
    X_UA_COMPATIBLE("X-UA-Compatible"),
    X_CONTENT_DURATION("X-Content-Duration");

    private final String mName;

    HeaderResponseField(final String name) {
        mName = name;
    }

    public final String getName() {
        return mName;
    }

    public final String toString() {
        return mName;
    }

}
