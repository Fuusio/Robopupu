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

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.test.suitebuilder.annotation.SmallTest;

import com.robopupu.api.dependency.D;
import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.util.Params;
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

    private FooApp fooApp;
    private Context applicationContext;
    private FeatureContainer featureContainer;
    private TestFeatureManager featureManager;
    private FragmentManager fragmentManager;
    private TestFeature testFeature;
    private TestView testView;
    private TestPresenter testPresenter;

    @Before
    public void beforeTests() {

        applicationContext = InstrumentationRegistry.getContext();
        fragmentManager = Mockito.mock(FragmentManager.class);
        featureManager = new TestFeatureManager();
        featureContainer = Mockito.mock(FeatureContainer.class);
    }

    @Test
    public void test() {

        assertNotNull(featureManager);

        final Params params = new Params ("foo", "bar");

        testFeature = (TestFeature) featureManager.startFeature(featureContainer, TestFeature.class, params);
        assertNotNull(testFeature);
        assertTrue(testFeature.isStarted());
        assertNotNull(testFeature.getBar());

        final DependencyScope scope = testFeature.getOwnedScope();
        assertNotNull(scope);
        assertEquals(D.getActiveScope(), scope);

        testFeature.pause();
        assertTrue(testFeature.isPaused());
        assertTrue(featureManager.mFeaturePaused);

        testFeature.resume();
        assertTrue(testFeature.isResumed());
        assertTrue(featureManager.mFeatureResumed);

        testFeature.finish();
        assertTrue(testFeature.isStopped());
        assertTrue(featureManager.mFeatureStopped);

        testFeature.destroy();
        assertTrue(testFeature.isDestroyed());
        assertTrue(featureManager.mFeatureDestroyed);
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
            super.onFeatureResumed(feature);
            mFeatureResumed = true;
        }

        @Override
        public void onFeaturePaused(Feature feature, boolean finishing) {
            super.onFeaturePaused(feature, finishing);
            mFeaturePaused = true;
        }

        @Override
        public void onFeatureStopped(Feature feature) {
            super.onFeatureStopped(feature);
            mFeatureStopped = true;
        }

        @Override
        public void onFeatureDestroyed(Feature feature) {
            super.onFeatureDestroyed(feature);
            mFeatureDestroyed = true;
        }
    }
}
