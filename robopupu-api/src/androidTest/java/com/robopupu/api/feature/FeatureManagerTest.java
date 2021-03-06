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
package com.robopupu.api.feature;

import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.test.suitebuilder.annotation.SmallTest;

import com.robopupu.api.dependency.D;
import com.robopupu.api.dependency.Dependency;
import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.util.Params;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class FeatureManagerTest {

    private FooApp mFooApp;
    private FeatureContainer mFragmentContainer;
    private FeatureManager mFeatureManager;
    private FragmentManager mFragmentManager;

    public FeatureManagerTest() {
        mFooApp = new FooApp();
        Dependency.setAppScope(new FooAppScope(mFooApp));
    }

    @Before
    public void beforeTests() {
        mFragmentManager = Mockito.mock(FragmentManager.class);
        mFeatureManager = new TestFeatureManager();
    }

    @After
    public void afterTests() {
        Dependency.disposeScopes();
    }

    @Test
    public void test() {

        mFragmentContainer = Mockito.mock(FeatureContainer.class);
        assertNotNull(mFeatureManager);

        final Params params = new Params("Foo", "Bar");
        final TestFeature feature = (TestFeature)mFeatureManager.startFeature(mFragmentContainer, TestFeature.class, params);

        assertNotNull(feature);

        final DependencyScope scope = feature.getOwnedScope();
        assertNotNull(scope);
        assertEquals(D.getActiveScope(), scope);

        List<Feature> activeFeatures = mFeatureManager.getActiveFeatures();

        assertFalse(activeFeatures.isEmpty());



    }

    private class TestFeatureManager extends AbstractFeatureManager {

    }
}
