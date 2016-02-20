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
package org.fuusio.api.util;

public class KeyValue<T_Key, T_Value> {

    private T_Key mKey;
    private T_Value mValue;

    public KeyValue(final T_Key key, final T_Value value) {
        mKey = key;
        mValue = value;
    }

    public KeyValue(final KeyValue<T_Key, T_Value> source) {
        mKey = source.mKey;
        mValue = source.mValue;
    }

    public final T_Key getKey() {
        return mKey;
    }

    public void setKey(final T_Key key) {
        mKey = key;
    }

    public final T_Value getValue() {
        return mValue;
    }

    public void setValue(final T_Value value) {
        mValue = value;
    }
}
