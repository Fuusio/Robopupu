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

public class Tuple<T_Value> {

    public T_Value value1;
    public T_Value value2;

    public Tuple(final T_Value value1, final T_Value value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public final T_Value getValue1() {
        return value1;
    }

    public void setValue1(final T_Value value) {
        this.value1 = value;
    }

    public final T_Value getValue2() {
        return value2;
    }

    public void setValue2(final T_Value value) {
        this.value2 = value;
    }
}
