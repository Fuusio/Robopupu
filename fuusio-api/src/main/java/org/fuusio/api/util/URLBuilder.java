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
package org.fuusio.api.util;

public class URLBuilder {

    private final StringBuilder mBuilder;

    private boolean mFirstParameter;

    public URLBuilder(final String url) {
        mBuilder = new StringBuilder(url);
        mFirstParameter = true;
    }

    public URLBuilder(final String formattedUrl, final Object... args) {
        mBuilder = new StringBuilder(StringToolkit.formatString(formattedUrl, args));
        mFirstParameter = true;
    }

    public URLBuilder p(final String key, final int value) {
        return addParam(key, Integer.toString(value));
    }

    public URLBuilder p(final String key, final boolean value) {
        return addParam(key, Boolean.toString(value));
    }

    public URLBuilder p(final String key, final float value) {
        return addParam(key, Float.toString(value));
    }

    public URLBuilder p(final String key, final double value) {
        return addParam(key, Double.toString(value));
    }

    public URLBuilder p(final String key, final long value) {
        return addParam(key, Long.toString(value));
    }

    public URLBuilder p(final String key, final String value) {
        return addParam(key, value);
    }

    public URLBuilder addParam(final String key, final String value) {
        if (mFirstParameter) {
            mBuilder.append('?');
            mFirstParameter = false;
        } else {
            mBuilder.append('&');
        }
        mBuilder.append(key);
        mBuilder.append('=');
        mBuilder.append(value);
        return this;
    }

    @Override
    public String toString() {
        return mBuilder.toString();
    }
}
