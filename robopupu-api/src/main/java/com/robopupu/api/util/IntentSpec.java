/*
 * Copyright (C) 2015 - 2016 Marko Salmela, http://robopupu.com
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

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link IntentSpec} is utility class that can be used to constuct Android {@code Intent}s without
 * references to Android platform APIs.
 */

public class IntentSpec {

    private final HashMap<String, String> categories;
    private final HashMap<String, TypedValue> extras;

    private String action;
    private URI data;
    private int flags;
    private String type;

    public IntentSpec() {
        categories = new HashMap<>();
        extras = new HashMap<>();
    }

    public HashMap<String, TypedValue> getExtras() {
        return extras;
    }

    public String getAction() {
        return action;
    }

    public Set<String> getCategories() {
        return new HashSet<>(categories.values());
    }

    public URI getData() {
        return data;
    }

    public int getFlags() {
        return flags;
    }

    public String getType() {
        return type;
    }

    public IntentSpec addCategory(final String category) {
        if (StringToolkit.isEmpty(category)) {
            throw new IllegalArgumentException("Parameter category may not be null nor empty");
        }
        categories.put(category, category);
        return this;
    }

    public IntentSpec addFlag(final int flag) {
        flags |= flag;
        return this;
    }

    public IntentSpec setAction(final String action) {
        this.action = action;
        return this;
    }

    public IntentSpec setData(final URI data) {
        this.data = data;
        return this;
    }

    public IntentSpec setDataAndType(final URI data, final String type) {
        this.data = data;
        this.type = type;
        return this;
    }

    public IntentSpec setType(final String type) {
        this.type = type;
        return this;
    }

    public IntentSpec putExtra(String name, final boolean value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final boolean[] value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final byte value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final byte[] value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final char value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final char[] value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final CharSequence value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final CharSequence[] value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final double value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final double[] value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final int value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final int[] value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final long value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final long[] value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final short value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final short[] value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final String value) {
        extras.put(name, new TypedValue(value));
        return this;
    }

    public IntentSpec putExtra(String name, final String[] value) {
        extras.put(name, new TypedValue(value));
        return this;
    }
}
