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
public class StringListTest {

    private String STRING_A1 = "A1";
    private String STRING_A2 = "A2";
    private String STRING_A3 = "A3";
    private String STRING_1_ELEMENT = "A1";
    private String STRING_2_ELEMENTS = "A1;A2";
    private String STRING_3_ELEMENTS = "A1;A2;A3";

    StringList stringList;

    @Before
    public void beforeTests() {
        stringList = new StringList();
        stringList.add(STRING_A1);
        stringList.add(STRING_A2);
        stringList.add(STRING_A3);
    }

    @Test
    public void testReadFromString() {

        // Test null String

        stringList.readFromString(null);

        assertEquals(0, stringList.size());

        // Test empty String

        stringList.readFromString("");

        assertEquals(0, stringList.size());

        // Test String containing just one element

        stringList.readFromString(STRING_1_ELEMENT);

        assertEquals(1, stringList.size());
        assertEquals(STRING_A1, stringList.get(0));

        // Test String containing two elements

        stringList.readFromString(STRING_2_ELEMENTS);

        assertEquals(2, stringList.size());
        assertEquals(STRING_A1, stringList.get(0));
        assertEquals(STRING_A2, stringList.get(1));

        // Test String containing three elements

        stringList.readFromString(STRING_3_ELEMENTS);

        assertEquals(3, stringList.size());
        assertEquals(STRING_A1, stringList.get(0));
        assertEquals(STRING_A2, stringList.get(1));
        assertEquals(STRING_A3, stringList.get(2));
    }

    @Test
    public void testWiteToString() {

        String result = null;

        // Test null String

        stringList.readFromString(null);

        result = stringList.writeToString();
        assertEquals("", result);

        // Test empty String

        stringList.readFromString("");

        result = stringList.writeToString();
        assertEquals("", result);

        // Test String containing just one element

        stringList.readFromString(STRING_1_ELEMENT);

        result = stringList.writeToString();
        assertEquals(STRING_1_ELEMENT, result);

        // Test String containing two elements

        stringList.readFromString(STRING_2_ELEMENTS);

        result = stringList.writeToString();
        assertEquals(STRING_2_ELEMENTS, result);

        // Test String containing three elements

        stringList.readFromString(STRING_3_ELEMENTS);

        result = stringList.writeToString();
        assertEquals(STRING_3_ELEMENTS, result);

        int a = 0;
    }

    @After
    public void afterTests() {
        stringList.clear();
    }
}
