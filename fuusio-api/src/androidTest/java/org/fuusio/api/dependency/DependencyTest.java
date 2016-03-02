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
package org.fuusio.api.dependency;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DependencyTest {

    private ZooApp mApp;
    private ZooAppScope mAppScope;
    private CityScope mParentScope;
    private ZooScopeOwner mScopeOwner;

    @Before
    public void beforeTests() {
        mApp = new ZooApp();
        mAppScope = new ZooAppScope(mApp);
        mParentScope = new CityScope();
        mScopeOwner = new ZooScopeOwner();
    }

    @After
    public void afterTests() {
        Dependency.disposeScopes();
    }

    @Test
    public void test() {

        // Test getAppScope() and setAppScope(...)

        Dependency.resetAppScope();
        assertNull(Dependency.getAppScope());
        Dependency.setAppScope(mAppScope);
        assertNotNull(Dependency.getAppScope());

        // Test addScope(...) and getScope(...) methods

        assertNull(Dependency.getScope(ZooScope.class, false));
        assertNull(Dependency.getScope(ZooScope.class.getName(), false));
        assertNull(Dependency.getScope(mScopeOwner, false));

        assertNotNull(Dependency.getScope(PubScope.class, true));
        assertNotNull(Dependency.getScope(PubScope.class, false));

        ZooScope scope = Dependency.getScope(mScopeOwner, true);
        assertNotNull(scope);
        assertNotNull(Dependency.getScope(ZooScope.class, false));
        mScopeOwner.setOwnedScope(scope);

        // Test disposeScope(...) method

        assertFalse(scope.wasDisposed());
        Dependency.disposeScope(mScopeOwner);
        assertNull(Dependency.getScope(ZooScope.class, false));
        assertTrue(scope.wasDisposed());
        assertTrue(scope.isCleared());

        // Test activateScope(...) method

        Dependency.resetActiveScope();
        assertNotNull(Dependency.getActiveScope());
        assertTrue(Dependency.getActiveScope() == mAppScope);
        Dependency.activateScope(mScopeOwner);
        assertNotNull(Dependency.getActiveScope());
        assertTrue(Dependency.getActiveScope() != mAppScope);

        assertNotNull(D.get(ZooApp.class));
        assertNotNull(D.get(Lion.class));
        assertNotNull(D.get(Elephant.class));

        final Banana banana = new Banana();
        final Monkey monkey = D.get(Monkey.class, banana);
        assertNotNull(monkey);
        assertEquals(monkey.getBanana(), banana);

        assertNotNull(D.get(Zoo.class));
        assertNull(D.get(Funding.class));

        scope = D.getScope(mScopeOwner, false);
        scope.setParentScope(mParentScope);

        assertNotNull(D.get(Funding.class));

        boolean wasExceptionCaught = false;

        final PubScope fooScope = new PubScope();
        try {
            D.activateScope(mScopeOwner, fooScope);
        } catch (IllegalArgumentException e) {
            wasExceptionCaught = true;
        }

        assertTrue(wasExceptionCaught);
    }
}
