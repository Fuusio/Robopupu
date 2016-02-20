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

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import org.fuusio.api.component.AbstractManager;
import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependenciesCache;
import org.fuusio.api.dependency.Dependency;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.DependencyScopeOwner;
import org.fuusio.api.dependency.Scopeable;
import org.fuusio.api.mvp.View;
import org.fuusio.api.util.Params;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link FeatureManagerImpl} implements a manager object for {@link Feature}a.
 */
public class FeatureManagerImpl extends AbstractManager
        implements FeatureManager, Application.ActivityLifecycleCallbacks {

    private static final String SUFFIX_IMPL = "Impl";

    private static FeatureManagerImpl sInstance = null;
    private static DependencyScopeOwner sMockScopeOwner = null;

    private final ArrayList<Feature> mActiveFeatures;
    private final DependenciesCache mDependenciesCache;

    private Activity mForegroundActivity;
    private Activity mLastPausedActivity;
    private Activity mLastStoppedActivity;

    public FeatureManagerImpl() {
        mActiveFeatures = new ArrayList<>();
        mDependenciesCache = D.get(DependenciesCache.class);
    }

    @Override
    public Application.ActivityLifecycleCallbacks getActivityLifecycleCallback() {
        return this;
    }

    /**
     * Gets the {@link Activity} that is currently in foreground.
     * @return An {@link Activity}. May return {@code null}.
     */
    public Activity getForegroundActivity() {
        return mForegroundActivity;
    }

    /**
     * Gets the {@link Activity} that is the last paused {@link Activity}.
     * @return An {@link Activity}. May return {@code null}.
     */
    public Activity getLastPausedActivity() {
        return mLastPausedActivity;
    }

    /**
     * Gets the {@link Activity} that is the last stopped {@link Activity}.
     * @return An {@link Activity}. May return {@code null}.
     */
    public Activity getLastStoppedActivity() {
        return mLastStoppedActivity;
    }

    public static void setInstance(final FeatureManagerImpl instance) {
        sInstance = instance;
    }

    /**
     * Sets a {@link DependencyScopeOwner} that is used to provide a {@link DependencyScope}
     * containing mock dependencies for testing purposes.
     *
     * @param owner A {@link DependencyScopeOwner}.
     */
    public void setMockScopeOwner(final DependencyScopeOwner owner) {
        sMockScopeOwner = owner;
    }

    /**
     * Gets an instance of {@link Feature} that is used a mock. Mock {@link Feature}s are made available
     * by setting a {@link DependencyScope} that provides them using method
     *
     * @param featureClass A class specifying the {@link Feature}. The class may an interface or implementation
     *                  class as long as the implementation class name is the interface class name with
     *                  postfix {@code Impl}.
     * @return A {@link Feature}. May return {@code null}
     */
    @SuppressWarnings("unchecked")
    private <T extends Feature> T getMockFeature(final Class<T> featureClass) {
        T feature = null;

        if (sMockScopeOwner != null) {
            final DependencyScope savedScope = D.getActiveScope();
            D.activateScope(sMockScopeOwner);

            feature = D.get(featureClass);

            if (feature == null && featureClass.isInterface()) {
                final Class<T> implClass;
                try {
                    implClass = (Class<T>) Class.forName(featureClass.getName() + SUFFIX_IMPL);
                    feature = D.get(implClass);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            D.deactivateScope(sMockScopeOwner);

            if (savedScope != null) {
                D.activateScope(savedScope.getOwner());
            }
        }
        return feature;
    }

    /**
     * Gets the currently active {@link Feature}s.
     *
     * @return A {@link List} of {@link Feature}s.
     */
    public List<Feature> getActiveFeatures() {
        return mActiveFeatures;
    }

    public List<Feature> getForegroundFeatures() {
        final List<Feature> foregroundFeatures = new ArrayList<>();

        for (int i = mActiveFeatures.size() - 1; i >= 0; i--) {
            final Feature feature = mActiveFeatures.get(i);

            if (feature.hasForegroundView()) {
                foregroundFeatures.add(feature);
            }
        }
        return foregroundFeatures;
    }

    /**
     * Creates the specified {@link Feature}, but does not start it. If the feature is
     * a {@link DependencyScopeOwner} its {@link FeatureScope} is added to cache of
     * {@link DependencyScope}s.
     *
     * @param featureClass A {@link Feature}
     * @param container A {@link FeatureContainer}.
     * @param params    A {@link Params} containing parameters for the started {@link Feature}.
     * @param <T>       The type extended from {@link Feature}.
     * @return A {@link Feature}.
     */
    @SuppressWarnings("unchecked")
    public <T extends Feature> T createFeature(final Class<T> featureClass, final FeatureContainer container, final Params params) {

        T feature = getMockFeature(featureClass);

        if (feature == null) {
            Class<T> implClass = featureClass;

            try {
                if (featureClass.isInterface()) {
                    implClass = (Class<T>) Class.forName(featureClass.getName() + SUFFIX_IMPL);
                }

                final Class[] paramTypes = {FeatureContainer.class, Params.class};
                final Object[] paramValues = {container, params};
                final Constructor<T> constructor = implClass.getConstructor(paramTypes);
                feature = constructor.newInstance(paramValues);
                Dependency.addScope(feature);
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate Feature: " + featureClass.getName() + ". Reason: " + e.getMessage());
            }
        }
        return feature;
    }

    /**
     * Creates and starts the specified {@link Feature} whose {@link android.app.Fragment}s are hosted by
     * the given {@link FeatureContainer}.
     *
     * @param featureClass A {@link Class} specifying the {@link Feature} to be created and started.
     * @param featureContainer A {@link FeatureContainer}.
     * @param params    A {@link Params} containing parameters for the created and started {@link Feature}.
     * @param <T>       The type extended from {@link Feature}.
     * @return A {@link Feature}.
     */
    @SuppressWarnings("unchecked")
    public <T extends Feature> T startFeature(final Class<T> featureClass, final FeatureContainer featureContainer, final Params params) {
        return startFeature(featureClass, featureContainer, null, params);
    }

    /**
     * Creates and starts the specified {@link Feature} whose {@link android.app.Fragment}s are hosted by
     * the given {@link FeatureContainer}.
     *
     * @param featureClass A {@link Class} specifying the {@link Feature} to be created and started.
     * @param featureContainer A {@link FeatureContainer}.
     * @param featureOwnerView A {@link View} that owns the started {@link Feature}.
     * @param params    A {@link Params} containing parameters for the created and started {@link Feature}.
     * @param <T>       The type extended from {@link Feature}.
     * @return A {@link Feature}.
     */
    @SuppressWarnings("unchecked")
    public <T extends Feature> T startFeature(final Class<T> featureClass, final FeatureContainer featureContainer, final View featureOwnerView, final Params params) {
        T feature = getMockFeature(featureClass);

        if (feature == null) {
            feature = createFeature(featureClass, featureContainer, params);
        }

        final DependencyScope scope = feature.getScope();

        if (featureContainer instanceof Scopeable) {
            ((Scopeable)featureContainer).setScope(scope);
        }

        if (featureOwnerView instanceof Scopeable) {
            ((Scopeable)featureOwnerView).setScope(scope);
        }

        return startFeature(feature, params);
    }

    /**
     * Starts the given {@link Feature}.
     *
     * @param feature   {The {@link Feature} to be started.
     * @param params A {@link Params} containing parameters for the started {@link Feature}.
     * @param <T>    The type extended from {@link Feature}.
     * @return A {@link Feature}.
     */
    public <T extends Feature> T startFeature(final T feature, final Params params) {
        feature.setFeatureManager(sInstance);
        Dependency.activateScope(feature);

        feature.start(params);
        sInstance.mActiveFeatures.add(feature);

        return feature;
    }

    /**
     * Invoked to handle Back Pressed event received by the {@link FeatureContainer}.
     *
     * @return A {@code boolean} value indicating if the event was consumed by this method.
     */
    public boolean onBackPressed() {

        final List<Feature> foregroundFeatures = getForegroundFeatures();

        for (int i = foregroundFeatures.size() - 1; i >= 0; i--) {
            final Feature feature = foregroundFeatures.get(i);

            if (feature.hasFocusedView()) {
                if (feature.isBackPressedEventHandler()) {
                    if (feature.canGoBack()) {
                        feature.goBack();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Invoked by a {@link Feature#resume()} when the {@link Feature}} has been resumed.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    public void onFeatureResumed(final Feature feature) {
        mActiveFeatures.add(feature);
    }

    /**
     * Invoked by a {@link Feature#pause()} when the {@link Feature}} has been paused.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    public void onFeaturePaused(final Feature feature) {
        mActiveFeatures.remove(feature);
    }


    /**
     * Invoked by a {@link Feature#stop()} when the {@link Feature}} has been stopped.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    public void onFeatureStopped(final Feature feature) {
        mActiveFeatures.remove(feature);
    }


    /**
     * Invoked by a {@link Feature#destroy()} when the {@link Feature}} has been destroyed.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    public void onFeatureDestroyed(final Feature feature) {
        mActiveFeatures.remove(feature);
        mDependenciesCache.removeDependencyScope(feature);
    }

    @Override
    public void onActivityCreated(final Activity activity, final Bundle inState) {
        // Do nothing by default
    }

    @Override
    public void onActivityStarted(final Activity activity) {
        // Do nothing by default
    }

    @Override
    public void onActivityResumed(final Activity activity) {
        mForegroundActivity = activity;

        if (activity == mLastPausedActivity) {
            mLastPausedActivity = null;
        }

        if (activity == mLastStoppedActivity) {
            mLastStoppedActivity = null;
        }
    }

    @Override
    public void onActivityPaused(final Activity activity) {
        if (activity == mForegroundActivity) {
            mForegroundActivity = null;
        }
        mLastPausedActivity = activity;
    }

    @Override
    public void onActivityStopped(final Activity activity) {
        if (activity == mLastPausedActivity) {
            mLastPausedActivity = null;
        }
        mLastStoppedActivity = activity;
    }

    @Override
    public void onActivitySaveInstanceState(final Activity activity, final Bundle outState) {
        // Do nothing by default
    }

    @Override
    public void onActivityDestroyed(final Activity activity) {
        if (activity == mLastStoppedActivity) {
            mLastStoppedActivity = null;
        }

        // Clear up the DependenciesCache for the destroyed Activity

        if (activity instanceof DependencyScopeOwner) {
            mDependenciesCache.onDependencyScopeOwnerDestroyed((DependencyScopeOwner) activity);
        }

        if (activity instanceof View) {
            mDependenciesCache.removeDependencies((View)activity);
        }
    }
}
