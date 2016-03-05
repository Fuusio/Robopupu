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
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.util.Log;

import org.fuusio.api.component.AbstractManager;
import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependenciesCache;
import org.fuusio.api.dependency.Dependency;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.DependencyScopeOwner;
import org.fuusio.api.mvp.View;
import org.fuusio.api.util.Params;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * {@link AbstractFeatureManager} provides a default implementation of {@link FeatureManager}.
 */
public abstract class AbstractFeatureManager extends AbstractManager
        implements FeatureManager, Application.ActivityLifecycleCallbacks {

    private static final String TAG = AbstractFeatureManager.class.getSimpleName();
    private static final String SUFFIX_IMPL = "Impl";

    private static DependencyScopeOwner sMockScopeOwner = null;

    private final HashSet<Feature> mPausedFeatures;
    private final HashSet<Feature> mResumedFeatures;
    private final DependenciesCache mDependenciesCache;
    private final HashMap<Class<? extends FeatureContainer>, Feature> mFeatureContainers;

    private Activity mForegroundActivity;
    private Activity mLastPausedActivity;
    private Activity mLastStoppedActivity;

    public AbstractFeatureManager() {
        mPausedFeatures = new HashSet<>();
        mResumedFeatures = new HashSet<>();
        mDependenciesCache = D.get(DependenciesCache.class);
        mFeatureContainers = new HashMap<>();
    }

    @Override
    public Application.ActivityLifecycleCallbacks getActivityLifecycleCallback() {
        return this;
    }

    @Override
    public Activity getForegroundActivity() {
        return mForegroundActivity;
    }

    @Override
    public Activity getLastPausedActivity() {
        return mLastPausedActivity;
    }

    @Override
    public Activity getLastStoppedActivity() {
        return mLastStoppedActivity;
    }

    @Override
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
    private Feature getMockFeature(final Class<? extends Feature> featureClass) {
        Feature feature = null;

        if (sMockScopeOwner != null) {
            final DependencyScope savedScope = D.getActiveScope();
            D.activateScope(sMockScopeOwner);

            feature = D.get(featureClass);

            if (feature == null && featureClass.isInterface()) {
                final Class<? extends Feature> implClass;
                try {
                    implClass = (Class<? extends Feature>) Class.forName(featureClass.getName() + SUFFIX_IMPL);
                    feature = D.get(implClass);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            D.deactivateScope(sMockScopeOwner);
            D.activateScope(savedScope.getOwner());
        }
        return feature;
    }

    @Override
    @NonNull
    public List<Feature> getActiveFeatures() {
        final ArrayList<Feature> activeFeatures = new ArrayList<>();
        activeFeatures.addAll(mResumedFeatures);
        return activeFeatures;
    }

    @Override
    @NonNull
    public List<Feature> getPausedFeatures() {
        final ArrayList<Feature> pausedFeatures = new ArrayList<>();
        pausedFeatures.addAll(mPausedFeatures);
        return pausedFeatures;
    }

    @Override
    @NonNull
    public List<Feature> getForegroundFeatures() {
        final List<Feature> foregroundFeatures = new ArrayList<>();

        for (final Feature feature : mResumedFeatures) {

            if (feature.hasForegroundView()) {
                foregroundFeatures.add(feature);
            }
        }
        return foregroundFeatures;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Feature createFeature(final Class<? extends Feature> featureClass) {
        Feature feature = getMockFeature(featureClass);

        if (feature == null) {

            // Feature implementations should be provided in application level DependencyScope so
            // that implementation for it can be found via Dependency#get(Class) method.
            feature = D.get(featureClass);

            // If the implemenation is not provided in a DependencyScope we attempt to instantiate
            // directly via reflection.
            if (feature == null) {
                Class<? extends Feature> implClass = featureClass;

                try {
                    if (featureClass.isInterface()) {
                        implClass = (Class<? extends Feature>) Class.forName(featureClass.getName() + SUFFIX_IMPL);
                    }

                    feature = implClass.newInstance();
                } catch (Exception e) {
                    Log.d(TAG, "Failed to instantiate Feature: " + featureClass.getName() + ". Reason: " + e.getMessage());
                }

                if (feature == null) {
                    try {
                        feature = featureClass.newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to instantiate Feature: " + featureClass.getName() + ". Reason: " + e.getMessage());
                    }
                }
            }
        }
        return feature;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Feature startFeature(final FeatureContainer featureContainer, final Class<? extends Feature> featureClass) {
        return startFeature(featureContainer, featureClass, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Feature startFeature(final FeatureContainer featureContainer, final Class<? extends Feature> featureClass, final Params params) {
        Feature feature = getMockFeature(featureClass);

        if (feature == null) {
            feature = createFeature(featureClass);
        }
        return startFeature(featureContainer, feature, params);
    }

    @Override
    public Feature startFeature(final FeatureContainer featureContainer, final Feature feature) {
        return startFeature(featureContainer, feature, null);
    }

    @Override
    public Feature startFeature(final FeatureContainer featureContainer, final Feature feature, final Params params) {

        if (featureContainer != null) {
            final Class<? extends FeatureContainer> key = featureContainer.getClass();
            final Feature previousFeature = mFeatureContainers.get(key);

            mFeatureContainers.put(key, feature);

            // Activity Features are finished by the Activities that own them.
            if (previousFeature != null && !previousFeature.isActivityFeature() && previousFeature != feature) {
                previousFeature.finish();
            }
        }

        feature.setFeatureManager(this);
        feature.setFeatureContainer(featureContainer);
        feature.start(params);
        mResumedFeatures.add(feature);
        mPausedFeatures.remove(feature);

        if (feature instanceof DependencyScopeOwner) {
            Dependency.activateScope((DependencyScopeOwner)feature);
        }
        return feature;
    }

    /**
     * Invoked to handle Back Pressed event received by the {@link FeatureContainer}.
     *
     * @return A {@code boolean} value indicating if the event was consumed by this method.
     */
    public boolean onBackPressed() {

        final List<Feature> foregroundFeatures = getForegroundFeatures();

        for (final Feature feature : foregroundFeatures) {
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
    @CallSuper
    public void onFeatureResumed(final Feature feature) {
        mPausedFeatures.remove(feature);
        mResumedFeatures.add(feature);
    }

    /**
     * Invoked by a {@link Feature#pause()} when the {@link Feature}} has been paused.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    @CallSuper
    public void onFeaturePaused(final Feature feature) {
        mPausedFeatures.add(feature);
        mResumedFeatures.remove(feature);
    }


    /**
     * Invoked by a {@link Feature#stop()} when the {@link Feature}} has been stopped.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    @CallSuper
    public void onFeatureStopped(final Feature feature) {
        mPausedFeatures.remove(feature);
        mResumedFeatures.remove(feature);
    }


    /**
     * Invoked by a {@link Feature#destroy()} when the {@link Feature}} has been destroyed.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    @CallSuper
    public void onFeatureDestroyed(final Feature feature) {
    }

    @Override
    public void onActivityCreated(final Activity activity, final Bundle inState) {
        // Do nothing by default
    }

    @Override
    public void onActivityStarted(final Activity activity) {
        if (activity instanceof FeatureContainerActivity) {
            final List<FeatureContainer> containers = ((FeatureContainerActivity)activity).getFeatureContainers();

            for (final FeatureContainer container : containers) {
                final Feature feature = mFeatureContainers.get(container.getClass());

                if (feature != null) {
                    feature.onFeatureContainerStarted(container);
                }
            }
        }
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

        if (activity instanceof FeatureContainerActivity) {
            final List<FeatureContainer> containers = ((FeatureContainerActivity)activity).getFeatureContainers();

            for (final FeatureContainer container : containers) {
                final Feature feature = mFeatureContainers.get(container.getClass());

                if (feature != null) {
                    feature.onFeatureContainerResumed(container);
                }
            }
        }
    }

    @Override
    public void onActivityPaused(final Activity activity) {
        if (activity == mForegroundActivity) {
            mForegroundActivity = null;
        }
        mLastPausedActivity = activity;

        if (activity instanceof FeatureContainerActivity) {
            final List<FeatureContainer> containers = ((FeatureContainerActivity)activity).getFeatureContainers();

            for (final FeatureContainer container : containers) {
                final Feature feature = mFeatureContainers.get(container.getClass());

                if (feature != null) {
                    feature.onFeatureContainerPaused(container);
                }
            }
        }
    }

    @Override
    public void onActivityStopped(final Activity activity) {
        if (activity == mLastPausedActivity) {
            mLastPausedActivity = null;
        }
        mLastStoppedActivity = activity;

        if (activity instanceof FeatureContainerActivity) {
            final List<FeatureContainer> containers = ((FeatureContainerActivity)activity).getFeatureContainers();

            for (final FeatureContainer container : containers) {
                final Feature feature = mFeatureContainers.get(container.getClass());

                if (feature != null) {
                    feature.onFeatureContainerStopped(container);
                }
            }
        }
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
