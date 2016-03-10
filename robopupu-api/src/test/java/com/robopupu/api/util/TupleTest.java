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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SmallTest
public class TupleTest {

    private static final double DOUBLE_VALUE_1 = 1.2345678;
    private static final double DOUBLE_VALUE_2 = 2.3456789;

    private Tuple<Double> mTuple;

    @Before
    public void beforeTests() {
        mTuple = new Tuple<>(DOUBLE_VALUE_1, DOUBLE_VALUE_2);
    }

    @After
    public void afterTests() {

    }

    @Test
    public void testGetValue1() {
        mTuple.setValue1(DOUBLE_VALUE_1);
        assertEquals(DOUBLE_VALUE_1, mTuple.getValue1(), 0);
    }

    @Test
    public void testSetValue1() {
        mTuple.setValue1(DOUBLE_VALUE_2);
        assertEquals(DOUBLE_VALUE_2, mTuple.getValue1(), 0);
    }

    @Test
    public void testGetValue2() {
        mTuple.setValue2(DOUBLE_VALUE_1);
        assertEquals(DOUBLE_VALUE_1, mTuple.getValue2(), 0);
    }

    @Test
    public void testSetValue2() {
        mTuple.setValue2(DOUBLE_VALUE_2);
        assertEquals(DOUBLE_VALUE_2, mTuple.getValue2(), 0);
    }
}