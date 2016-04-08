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
/**
 * {@link HttpMethod} defines an enumerated type for representing HTTP methods.
 */
public enum HttpMethod {

    /**
     * Supported request methods.
     */
    DEPRECATED_GET_OR_POST("DEPRECATED_GET_OR_POST", -1),
    GET("GET", 0),
    POST("POST", 1),
    PUT("PUT", 2),
    DELETE("DELETE", 3),
    HEAD("HEAD", 4),
    OPTIONS("OPTIONS", 5),
    TRACE("TRACE", 6),
    PATCH("PATCH", 7);

    private final int mId;
    private final String mName;

    HttpMethod(final String name, final int id) {
        mName = name;
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public final String getName() {
        return mName;
    }

    public boolean isDelete() {
        return (this == HttpMethod.DELETE);
    }

    public boolean isGet() {
        return (this == HttpMethod.GET);
    }

    public boolean isPost() {
        return (this == HttpMethod.POST);
    }

    public boolean isPut() {
        return (this == HttpMethod.PUT);
    }

    public final String toString() {
        return mName;
    }
}
