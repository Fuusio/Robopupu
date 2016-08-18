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

import com.google.gson.JsonObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@SmallTest
public class GsonObjectTest {

    private Foo foo;

    @Before
    public void beforeTests() {
        foo = new Foo();
    }

    @After
    public void afterTests() {}

    @Test
    public void test() {

        final JsonObject jsonObject = foo.toJsonObject();
        final Foo foo2 = Foo.fromJson(jsonObject, Foo.class);

        assertTrue(foo2.booleanValue && foo2.booleanValue == foo.booleanValue);
        assertTrue(foo2.floatValue == foo.floatValue);
        assertTrue(foo2.intValue == foo.intValue);
        assertTrue(foo2.stringValue.contentEquals(foo.stringValue));
    }

    public class Foo extends GsonObject {

        private boolean booleanValue;
        private float floatValue;
        private int intValue;
        private String stringValue;

        public Foo() {
            booleanValue = true;
            floatValue = 3.14159f;
            intValue = 123;
            stringValue = "abc";
        }
    }
}
