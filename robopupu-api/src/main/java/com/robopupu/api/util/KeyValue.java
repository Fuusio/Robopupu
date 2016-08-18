/*
 * Copyright (C) 2009 Marko Salmela.
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

public class KeyValue<T_Key, T_Value> {

    public T_Key key;
    public T_Value value;

    public KeyValue(final T_Key key, final T_Value value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue(final KeyValue<T_Key, T_Value> source) {
        key = source.key;
        value = source.value;
    }

    public final T_Key getKey() {
        return key;
    }

    public void setKey(final T_Key key) {
        this.key = key;
    }

    public final T_Value getValue() {
        return value;
    }

    public void setValue(final T_Value value) {
        this.value = value;
    }
}
