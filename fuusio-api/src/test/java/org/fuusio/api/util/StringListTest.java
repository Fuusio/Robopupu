/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://fuusio.org
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

    StringList mStringList;

    @Before
    public void beforeTests() {
        mStringList = new StringList();
        mStringList.add(STRING_A1);
        mStringList.add(STRING_A2);
        mStringList.add(STRING_A3);
    }

    @Test
    public void testReadFromString() {

        // Test null String

        mStringList.readFromString(null);

        assertEquals(0, mStringList.size());

        // Test empty String

        mStringList.readFromString("");

        assertEquals(0, mStringList.size());

        // Test String containing just one element

        mStringList.readFromString(STRING_1_ELEMENT);

        assertEquals(1, mStringList.size());
        assertEquals(STRING_A1, mStringList.get(0));

        // Test String containing two elements

        mStringList.readFromString(STRING_2_ELEMENTS);

        assertEquals(2, mStringList.size());
        assertEquals(STRING_A1, mStringList.get(0));
        assertEquals(STRING_A2, mStringList.get(1));

        // Test String containing three elements

        mStringList.readFromString(STRING_3_ELEMENTS);

        assertEquals(3, mStringList.size());
        assertEquals(STRING_A1, mStringList.get(0));
        assertEquals(STRING_A2, mStringList.get(1));
        assertEquals(STRING_A3, mStringList.get(2));
    }

    @Test
    public void testWiteToString() {

        String result = null;

        // Test null String

        mStringList.readFromString(null);

        result = mStringList.writeToString();
        assertEquals("", result);

        // Test empty String

        mStringList.readFromString("");

        result = mStringList.writeToString();
        assertEquals("", result);

        // Test String containing just one element

        mStringList.readFromString(STRING_1_ELEMENT);

        result = mStringList.writeToString();
        assertEquals(STRING_1_ELEMENT, result);

        // Test String containing two elements

        mStringList.readFromString(STRING_2_ELEMENTS);

        result = mStringList.writeToString();
        assertEquals(STRING_2_ELEMENTS, result);

        // Test String containing three elements

        mStringList.readFromString(STRING_3_ELEMENTS);

        result = mStringList.writeToString();
        assertEquals(STRING_3_ELEMENTS, result);

        int a = 0;
    }

    @After
    public void afterTests() {
        mStringList.clear();
    }
}
