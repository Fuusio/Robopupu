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
package com.robopupu.api.dependency;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DependencyScopeTest {

    private ZooApp mApp;
    private ZooAppScope mAppScope;
    private CityScope mParentScope;

    @Before
    public void beforeTests() {
        mApp = new ZooApp();
        mAppScope = new ZooAppScope(mApp);
        mParentScope = new CityScope();
        Dependency.setAppScope(mAppScope);
    }

    @After
    public void afterTests() {
        Dependency.disposeScopes();
    }

    @Test
    public void test() {

        final PubScopeOwner scopeOwner = new PubScopeOwner();
        final PubScope pubScope = new PubScope();
        scopeOwner.setOwnedScope(pubScope);

        // Test isDisposable() method

        assertFalse(mAppScope.isDisposable());
        assertTrue(pubScope.isDisposable());

        // Test hasDependency(...) methods

        D.activateScope(scopeOwner);

        final Beer badBeer = new Beer();

        assertFalse(pubScope.hasDependency(Beer.class));
        assertFalse(pubScope.hasDependency(badBeer));

        final Beer goodBeer = D.get(Beer.class);

        assertTrue(pubScope.hasDependency(Beer.class));
        assertFalse(pubScope.hasDependency(badBeer));
        assertTrue(pubScope.hasDependency(goodBeer));

        // Test removeDependency(...) method

        final List<Class<?>> removedDependencyTypes = pubScope.removeDependency(goodBeer);

        assertEquals(removedDependencyTypes.size(), 1);
        assertTrue(removedDependencyTypes.contains(Beer.class));

        assertFalse(pubScope.hasDependency(goodBeer));

        assertNull(pubScope.getDependency(Whiskey.class, null, false));
        assertNotNull(pubScope.getDependency(Whiskey.class, null, true));

        // Test inialize()

        final ZooScopeOwner zooScopeOwner = new ZooScopeOwner();
        final ZooScope zooScope = new ZooScope();
        zooScopeOwner.setOwnedScope(zooScope);

        zooScope.initialize();

        assertNotNull(zooScope.getDependencyProvider());

        // Test getDependency(...) methods

        Assert.assertNotNull(zooScope.getDependency(ZooApp.class, null, false));
        Assert.assertNotNull(zooScope.getDependency(Lion.class, null, false));
        Assert.assertNotNull(zooScope.getDependency(Elephant.class, null, false));

        D.activateScope(zooScopeOwner);

        final Banana banana = new Banana();
        final Monkey monkey = zooScope.getDependency(Monkey.class, banana, false);
        Assert.assertNotNull(monkey);
        Assert.assertEquals(monkey.getBanana(), banana);

        Assert.assertNotNull(D.get(Zoo.class));
        assertNull(D.get(Funding.class));

        // Test dispose(...) method

        zooScope.dispose();

        assertFalse(zooScope.hasDependency(Lion.class));
        assertFalse(zooScope.hasDependency(Elephant.class));
        assertFalse(zooScope.hasDependency(Monkey.class));
        assertFalse(zooScope.hasDependency(Banana.class));
    }
}
