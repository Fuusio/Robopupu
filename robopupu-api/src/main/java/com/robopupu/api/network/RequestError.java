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

import java.util.Map;

/**
 * {@link RequestError} extends {@link Exception} to implement an object that can be used to store
 * information about errors.
 */
public class RequestError extends Exception {

    protected Throwable mCause;
    protected HttpHeaders mHeaders;
    protected String mMessage;
    protected long mNetworkTime;
    protected int mStatusCode;

    public RequestError() {
        /*
        mCause = error.getCause();
        mMessage = error.getMessage();
        mNetworkTime = error.getNetworkTimeMs();
        mStatusCode = error.networkResponse.statusCode;*/

    }

    /**
     * Gets the actual {@link Throwable} causing the error.
     * @return A {@link Throwable}
     */
    public final Throwable getCause() {
        return mCause;
    }

    /**
     * Sets the actual {@link Throwable} causing the error.
     * @param cause A {@link Throwable}.
     */
    public void setCause(final Throwable cause) {
        mCause = cause;
    }

    /**
     * Gets the HTTP headers.
     * @return A {@link HttpHeaders}.
     */
    public final HttpHeaders getHeaders() {
        return mHeaders;
    }

    /**
     * Sets the HTTP headers.
     * @param headers A {@link Map} containing headers.
     */
    public void setHeaders(final Map<String, String> headers) {
        mHeaders = new HttpHeaders(headers);
    }

    /**
     * Gets the error message.
     * @return The message as a {@link String}.
     */
    public final String getMessage() {
        return mMessage;
    }

    /**
     * Sets the error message.
     * @param message The message as a {@link String}.
     */
    public void setMessage(final String message) {
        mMessage = message;
    }

    /**
     * Gets the network time of the error.
     * @return The network time as a {@code long}.
     */
    public final long getNetworkTime() {
        return mNetworkTime;
    }

    /**
     * Sets the network time of the error.
     * @param networkTime The network time as a {@code long}.
     */
    public void setNetworkTime(final long networkTime) {
        mNetworkTime = networkTime;
    }

    /**
     * Gets the HTTP status code of the error response.
     * @return The HTTP status code as an {@code int}.
     */
    public final int getStatusCode() {
        return mStatusCode;
    }

    /**
     * Sets the HTTP status code of the error response.
     * @param statusCode The HTTP status code as an {@code int}.
     */
    public void setStatusCode(final int statusCode) {
        mStatusCode = statusCode;
    }
}
