/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://robopupu.com
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

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SmallTest
public class KeyValueTest {

    private static final double DOUBLE_VALUE_1 = 1.2345678;
    private static final double DOUBLE_VALUE_2 = 1.2345678;
    private static final String KEY = "Key";

    private KeyValue<String, Double> mKeyValue;

    @Test
    public void testGetValue() {
        mKeyValue = new KeyValue<>(KEY, DOUBLE_VALUE_1);
        assertEquals(DOUBLE_VALUE_1, mKeyValue.getValue(), 0);
    }

    @Test
    public void testSetValue() {
        mKeyValue = new KeyValue<>(KEY, DOUBLE_VALUE_1);
        mKeyValue.setValue(DOUBLE_VALUE_2);
        assertEquals(DOUBLE_VALUE_2, mKeyValue.getValue(), 0);
    }
}