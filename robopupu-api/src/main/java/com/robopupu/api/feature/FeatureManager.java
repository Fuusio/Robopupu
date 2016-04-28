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

import com.robopupu.api.component.Manager;
import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.dependency.DependencyScopeOwner;
import com.robopupu.api.mvp.View;
import com.robopupu.api.plugin.PluginStateComponent;
import com.robopupu.api.util.Params;

import java.util.List;

/**
 * {@link FeatureManager} defines an interface for {@link Manager} object that provides an API
 * for managing {@link Feature}s.
 */
public interface FeatureManager extends Manager  {

    /**
     * Gets an object that implements {@link Application.ActivityLifecycleCallbacks}.
     * @return An object that implements {@link Application.ActivityLifecycleCallbacks}.
     */
    Application.ActivityLifecycleCallbacks getActivityLifecycleCallback();

    /**
     * Gets the {@link Activity} that is currently in foreground.
     * @return An {@link Activity}. May return {@code null}.
     */
    Activity getForegroundActivity();

    /**
     * Gets the {@link Activity} that is the last paused {@link Activity}.
     * @return An {@link Activity}. May return {@code null}.
     */
    Activity getLastPausedActivity();

    /**
     * Gets the {@link Activity} that is the last stopped {@link Activity}.
     * @return An {@link Activity}. May return {@code null}.
     */
    Activity getLastStoppedActivity();

    /**
     * Sets a {@link DependencyScopeOwner} that is used to provide a {@link DependencyScope}
     * containing mock dependencies for testing purposes.
     *
     * @param owner A {@link DependencyScopeOwner}.
     */
    void setMockScopeOwner(DependencyScopeOwner owner);

    /**
     * Gets the currently active {@link Feature}s.
     *
     * @return A {@link List} of {@link Feature}s.
     */
    List<Feature> getActiveFeatures();

    /**
     * Gets the currently paused {@link Feature}s.
     *
     * @return A {@link List} of {@link Feature}s.
     */
    List<Feature> getPausedFeatures();

    /**
     * Gets all the {@link Feature}s that have {@link View}s in the foreground.
     *
     * @return A {@link List} of {@link Feature}s.
     */
    List<Feature> getForegroundFeatures();

    /**
     * Gets a {@link FeatureContainer} with the given ID.
     * @param featureContainerId The ID of the {@link FeatureContainer}.
     * @return A {@link FeatureContainer}.
     */
    FeatureContainer getFeatureContainer(int featureContainerId);

    /**
     * Creates the specified {@link Feature}, but does not start it. If the feature is
     * a {@link DependencyScopeOwner} its {@link DependencyScope} is added to cache of
     * {@link DependencyScope}s.
     *
     * @param featureClass A {@link Feature}
     * @return A {@link Feature}.
     */
    Feature createFeature(Class<? extends Feature> featureClass);

    /**
     * Creates and starts the specified {@link Feature} whose {@link FeatureCompatFragment}s are hosted by
     * the given {@link FeatureContainer}.
     *
     * @param featureContainer A {@link FeatureContainer}.
     * @param featureClass A {@link Class} specifying the {@link Feature} to be created and started.
     * @return A {@link Feature}.
     */
    Feature startFeature(FeatureContainer featureContainer, Class<? extends Feature> featureClass);

    /**
     * Creates and starts the specified {@link Feature} whose {@link FeatureCompatFragment}s are hosted by
     * the given {@link FeatureContainer}.
     *
     * @param featureContainer A {@link FeatureContainer}.
     * @param featureClass A {@link Class} specifying the {@link Feature} to be created and started.
     * @param params    A {@link Params} containing parameters for the created and started {@link Feature}.
     * @return A {@link Feature}.
     */
    Feature startFeature(FeatureContainer featureContainer, Class<? extends Feature> featureClass, Params params);

    /**
     * Starts the given {@link Feature} whose {@link FeatureCompatFragment}s are hosted by
     * the given {@link FeatureContainer}.
     *
     * @param featureContainer A {@link FeatureContainer}.
     * @param feature   {The {@link Feature} to be started.
     * @return A {@link Feature}.
     */
    Feature startFeature(FeatureContainer featureContainer, Feature feature);

    /**
     * Starts the given {@link Feature} whose {@link FeatureCompatFragment}s are hosted by
     * the given {@link FeatureContainer}.
     *
     * @param featureContainer A {@link FeatureContainer}.
     * @param feature   {The {@link Feature} to be started.
     * @param params A {@link Params} containing parameters for the started {@link Feature}.
     * @return A {@link Feature}.
     */
    Feature startFeature(FeatureContainer featureContainer, Feature feature, Params params);

    /**
     * Invoked to handle Back Pressed event received by the {@link FeatureContainer}.
     *
     * @return A {@code boolean} value indicating if the event was consumed by this method.
     */
    boolean onBackPressed();

    /**
     * Invoked by a {@link Feature#resume()} when the {@link Feature}} has been resumed.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    void onFeatureResumed(Feature feature);

    /**
     * Invoked by a {@link Feature#pause()} when the {@link Feature}} has been paused.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     * @param finishing A {@code boolean} value indicating if the {@link PluginStateComponent} is
     *                  going to be finished i.e. it is not resumed nor restarted anymore.
     */
    void onFeaturePaused(Feature feature, boolean finishing);

    /**
     * Invoked by a {@link Feature#stop()} when the {@link Feature}} has been stopped.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    void onFeatureStopped(Feature feature);

    /**
     * Invoked by a {@link Feature#destroy()} when the {@link Feature}} has been destroyed.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    void onFeatureDestroyed(Feature feature);

    /**
     * Register the given {@link FeatureContainerProvider} for this {@link FeatureManager} so
     * that {@link Feature}s can access their {@link FeatureContainer}s.
     * @param provider A {@link FeatureContainerProvider}.
     */
    void registerFeatureContainerProvider(FeatureContainerProvider provider);

    /**
     * Unregister the given {@link FeatureContainerProvider} from this {@link FeatureManager}.
     * @param provider A {@link FeatureContainerProvider}.
     */
    void unregisterFeatureContainerProvider(FeatureContainerProvider provider);
}
