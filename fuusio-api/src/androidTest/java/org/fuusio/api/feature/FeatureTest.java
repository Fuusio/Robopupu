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
package org.fuusio.api.feature;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.test.suitebuilder.annotation.SmallTest;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.util.Params;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class FeatureTest {

    private FooApp mFooApp;
    private Context mApplicationContext;
    private FeatureContainer mFeatureContainer;
    private TestFeatureManager mFeatureManager;
    private FragmentManager mFragmentManager;
    private TestFeature mTestFeature;
    private TestView mTestView;
    private TestPresenter mTestPresenter;

    @Before
    public void beforeTests() {

        mApplicationContext = InstrumentationRegistry.getContext();
        mFragmentManager = Mockito.mock(FragmentManager.class);
        mFeatureManager = new TestFeatureManager();
        mFeatureContainer = Mockito.mock(FeatureContainer.class);

        when(mFeatureContainer.getSupportFragmentManager()).thenReturn(mFragmentManager);
    }

    @Test
    public void test() {

        assertNotNull(mFeatureManager);

        final Params params = new Params ("foo", "bar");

        mTestFeature = (TestFeature)mFeatureManager.startFeature(mFeatureContainer, TestFeature.class, params);
        assertNotNull(mTestFeature);
        assertTrue(mTestFeature.isStarted());
        assertNotNull(mTestFeature.getBar());

        final DependencyScope scope = mTestFeature.getOwnedScope();
        assertNotNull(scope);
        assertEquals(D.getActiveScope(), scope);

        mTestFeature.pause();
        assertTrue(mTestFeature.isPaused());
        assertTrue(mFeatureManager.mFeaturePaused);

        mTestFeature.resume();
        assertTrue(mTestFeature.isResumed());
        assertTrue(mFeatureManager.mFeatureResumed);

        mTestFeature.finish();
        assertTrue(mTestFeature.isStopped());
        assertTrue(mFeatureManager.mFeatureStopped);

        mTestFeature.destroy();
        assertTrue(mTestFeature.isDestroyed());
        assertTrue(mFeatureManager.mFeatureDestroyed);
    }

    @After
    public void afterTests() {
    }

    private class TestFeatureManager extends AbstractFeatureManager {

        protected boolean mFeaturePaused = false;
        protected boolean mFeatureResumed = false;
        protected boolean mFeatureStopped = false;
        protected boolean mFeatureDestroyed = false;

        @Override
        public void onFeatureResumed(Feature feature) {
            mFeatureResumed = true;
        }

        @Override
        public void onFeaturePaused(Feature feature) {
            mFeaturePaused = true;
        }

        @Override
        public void onFeatureStopped(Feature feature) {
            mFeatureStopped = true;
        }

        @Override
        public void onFeatureDestroyed(Feature feature) {
            mFeatureDestroyed = true;
        }
    }
}
