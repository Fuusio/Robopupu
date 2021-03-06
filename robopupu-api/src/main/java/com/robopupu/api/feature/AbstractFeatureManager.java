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

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.robopupu.api.component.AbstractManager;
import com.robopupu.api.dependency.D;
import com.robopupu.api.dependency.DependenciesCache;
import com.robopupu.api.dependency.Dependency;
import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.dependency.DependencyScopeOwner;
import com.robopupu.api.mvp.View;
import com.robopupu.api.util.Params;
import com.robopupu.api.util.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * {@link AbstractFeatureManager} provides a default implementation of {@link FeatureManager}.
 */
public abstract class AbstractFeatureManager extends AbstractManager
        implements FeatureManager, Application.ActivityLifecycleCallbacks {

    private static final String TAG = Utils.tag(AbstractFeatureManager.class);
    private static final String SUFFIX_IMPL = "Impl";

    private static DependencyScopeOwner mockScopeOwner = null;
    private static FeatureManager instance = null;

    private final SparseArray<Feature> currentFeatures;
    private final DependenciesCache dependenciesCache;
    private final SparseArray<FeatureContainer> featureContainers;
    private final HashSet<Feature> pausedFeatures;
    private final HashSet<Feature> resumedFeatures;

    private Activity foregroundActivity;
    private Activity lastPausedActivity;
    private Activity lastStoppedActivity;

    public AbstractFeatureManager() {
        instance = this;
        pausedFeatures = new HashSet<>();
        resumedFeatures = new HashSet<>();
        dependenciesCache = D.get(DependenciesCache.class);
        currentFeatures = new SparseArray<>();
        featureContainers = new SparseArray<>();
    }

    public static FeatureManager getInstance() {
        return instance;
    }

    public static void setInstance(final FeatureManager instance) {
        AbstractFeatureManager.instance = instance;
    }

    @Override
    public List<Feature> getActiveChildFeatures(final Feature parentFeature) {
        final List<Feature> childFeatures = new ArrayList<>();

        for (final Feature feature : resumedFeatures) {
            if (feature.getParentFeature() == parentFeature) {
                childFeatures.add(feature);
            }
        }

        for (final Feature feature : pausedFeatures) {
            if (feature.getParentFeature() == parentFeature) {
                childFeatures.add(feature);
            }
        }
        return childFeatures;
    }

    @Override
    public boolean hasActiveChildFeatures(final Feature parentFeature) {
        return !getActiveChildFeatures(parentFeature).isEmpty();
    }

    @Override
    public Application.ActivityLifecycleCallbacks getActivityLifecycleCallback() {
        return this;
    }

    @Override
    public Activity getForegroundActivity() {
        return foregroundActivity;
    }

    @Override
    public Activity getLastPausedActivity() {
        return lastPausedActivity;
    }

    @Override
    public Activity getLastStoppedActivity() {
        return lastStoppedActivity;
    }

    @Override
    public void setMockScopeOwner(final DependencyScopeOwner owner) {
        mockScopeOwner = owner;
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

        if (mockScopeOwner != null) {
            final DependencyScope savedScope = D.getActiveScope();
            D.activateScope(mockScopeOwner);

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

            D.deactivateScope(mockScopeOwner);
            D.activateScope(savedScope.getOwner());
        }
        return feature;
    }

    @Override
    @NonNull
    public List<Feature> getActiveFeatures() {
        final ArrayList<Feature> activeFeatures = new ArrayList<>();
        activeFeatures.addAll(resumedFeatures);
        return activeFeatures;
    }

    @Override
    @NonNull
    public List<Feature> getPausedFeatures() {
        final ArrayList<Feature> pausedFeatures = new ArrayList<>();
        pausedFeatures.addAll(this.pausedFeatures);
        return pausedFeatures;
    }

    @Override
    @NonNull
    public List<Feature> getForegroundFeatures() {
        final List<Feature> foregroundFeatures = new ArrayList<>();

        for (final Feature feature : resumedFeatures) {

            if (feature.hasForegroundView()) {
                foregroundFeatures.add(feature);
            }
        }
        return foregroundFeatures;
    }

    @Override
    public FeatureContainer getFeatureContainer(final int featureContainerId) {
        return featureContainers.get(featureContainerId);
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

    @Override
    public Feature startFeature(final @IdRes int featureContainerId, final Class<? extends Feature> featureClass, final Params params) {
        final FeatureContainer featureContainer = getFeatureContainer(featureContainerId);
        return startFeature(featureContainer, featureClass, params);
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
            final int key = featureContainer.getContainerViewId();
            final Feature previousFeature = currentFeatures.get(key);

            currentFeatures.put(key, feature);

            // Activity Features are finished by the Activities that own them.
            if (previousFeature != null && !previousFeature.isActivityFeature() && previousFeature != feature) {
                previousFeature.finish();
            }
        }

        feature.setFeatureManager(this);
        feature.setFeatureContainer(featureContainer);
        feature.start(params);
        resumedFeatures.add(feature);
        pausedFeatures.remove(feature);

        if (feature instanceof DependencyScopeOwner) {
            Dependency.activateScope((DependencyScopeOwner)feature);
        }
        return feature;
    }

    @Override
    public Feature startFeature(final @IdRes int featureContainerId, final Feature feature, final Params params) {
        final FeatureContainer featureContainer = getFeatureContainer(featureContainerId);
        return startFeature(featureContainer, feature, params);
    }

    @Override
    public boolean conditionallyRestartFeature(final Feature feature, final FeatureView featureView) {
        if (feature == null) {
            return false;
        }

        feature.setFeatureManager(this);

        if (!feature.isStarted()) {
            final FeatureContainer featureContainer = feature.getFeatureContainer();
            DependencyScope scope = null;

            if (feature instanceof DependencyScopeOwner) {
                scope = ((DependencyScopeOwner) feature).getOwnedScope();
            }

            // The FeatureView is provided as an dependant because FeatureView typically has
            // dependencies and it itself a depandency within a Feature. Providing the  FeatureView
            // (e.g. a Fragment) as an dependant prevents it to be re-created.
            if (scope != null) {
                scope.addDependant(featureView);
            }
            feature.restart();
            startFeature(featureContainer, feature, feature.getParams());
            return true;
        }
        return false;
    }

    @Override
    public void registerFeatureContainerProvider(final FeatureContainerProvider provider) {
        for (final FeatureContainer container : provider.getFeatureContainers()) {
            featureContainers.put(container.getContainerViewId(), container);
        }
    }

    @Override
    public void unregisterFeatureContainerProvider(final FeatureContainerProvider provider) {
        for (final FeatureContainer container : provider.getFeatureContainers()) {
            featureContainers.remove(container.getContainerViewId());
        }
    }

    /**
     * Invoked to handle Back Pressed event received by the {@link FeatureContainer}.
     *
     * @return A {@code boolean} value indicating if the event was consumed by this method.
     */
    @Override
    public boolean onBackPressed() {

        final List<Feature> foregroundFeatures = getForegroundFeatures();

        for (final Feature feature : foregroundFeatures) {
            if (feature.hasForegroundView()) {
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

    @CallSuper
    @Override
    public void onFeatureResumed(final Feature feature) {
        pausedFeatures.remove(feature);
        resumedFeatures.add(feature);
    }

    @CallSuper
    @Override
    public void onFeaturePaused(final Feature feature, final boolean finishing) {
        pausedFeatures.add(feature);
        resumedFeatures.remove(feature);
    }

    @CallSuper
    @Override
    public void onFeatureStopped(final Feature feature) {
        pausedFeatures.remove(feature);
        resumedFeatures.remove(feature);

        final int index = currentFeatures.indexOfValue(feature);
        currentFeatures.remove(index);

        feature.setParentFeature(null);
    }

    @CallSuper
    @Override
    public void onFeatureDestroyed(final Feature feature) {
    }

    @Override
    public void onActivityCreated(final Activity activity, final Bundle inState) {
        // Do nothing by default
    }

    @Override
    public void onActivityStarted(final Activity activity) {
        if (activity instanceof FeatureContainerProvider) {
            final List<FeatureContainer> containers = ((FeatureContainerProvider)activity).getFeatureContainers();

            for (final FeatureContainer container : containers) {
                final Feature feature = currentFeatures.get(container.getContainerViewId());

                if (feature != null) {
                    feature.onFeatureContainerStarted(container);
                }
            }
        }
    }

    @Override
    public void onActivityResumed(final Activity activity) {
        foregroundActivity = activity;

        if (activity == lastPausedActivity) {
            lastPausedActivity = null;
        }

        if (activity == lastStoppedActivity) {
            lastStoppedActivity = null;
        }

        if (activity instanceof FeatureContainerProvider) {
            final List<FeatureContainer> containers = ((FeatureContainerProvider)activity).getFeatureContainers();

            for (final FeatureContainer container : containers) {
                final Feature feature = currentFeatures.get(container.getContainerViewId());

                if (feature != null) {
                    feature.onFeatureContainerResumed(container);
                }
            }
        }
    }

    @Override
    public void onActivityPaused(final Activity activity) {
        if (activity == foregroundActivity) {
            foregroundActivity = null;
        }
        lastPausedActivity = activity;

        if (activity instanceof FeatureContainerProvider) {
            final List<FeatureContainer> containers = ((FeatureContainerProvider)activity).getFeatureContainers();

            final boolean finishing = activity.isFinishing();

            for (final FeatureContainer container : containers) {
                final Feature feature = currentFeatures.get(container.getContainerViewId());

                if (feature != null) {
                    feature.onFeatureContainerPaused(container, finishing);
                }
            }
        }
    }

    @Override
    public void onActivityStopped(final Activity activity) {
        if (activity == lastPausedActivity) {
            lastPausedActivity = null;
        }
        lastStoppedActivity = activity;

        if (activity instanceof FeatureContainerProvider) {
            final List<FeatureContainer> containers = ((FeatureContainerProvider)activity).getFeatureContainers();

            final boolean finishing = activity.isFinishing();

            for (final FeatureContainer container : containers) {
                final Feature feature = currentFeatures.get(container.getContainerViewId());

                if (feature != null) {
                    feature.onFeatureContainerStopped(container, finishing);
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
        if (activity == lastStoppedActivity) {
            lastStoppedActivity = null;
        }

        // Clear up the DependenciesCache for the destroyed Activity

        if (activity instanceof DependencyScopeOwner) {
            dependenciesCache.onDependencyScopeOwnerDestroyed((DependencyScopeOwner) activity);
        }

        if (activity instanceof View) {
            dependenciesCache.removeDependencies((View)activity);
        }
    }
}
