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
import org.fuusio.api.component.Manager;
import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependenciesCache;
import org.fuusio.api.dependency.Dependency;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.DependencyScopeOwner;
import org.fuusio.api.dependency.Scopeable;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.PlugInterface;
import org.fuusio.api.util.Params;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
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
    void setMockScopeOwner(final DependencyScopeOwner owner);

    /**
     * Gets the currently active {@link Feature}s.
     *
     * @return A {@link List} of {@link Feature}s.
     */
    List<Feature> getActiveFeatures();

    List<Feature> getForegroundFeatures();

    /**
     * Creates the specified {@link Feature}, but does not start it. If the feature is
     * a {@link DependencyScopeOwner} its {@link FeatureScope} is added to cache of
     * {@link DependencyScope}s.
     *
     * @param featureClass A {@link Feature}
     * @return A {@link Feature}.
     */
    Feature createFeature(final Class<? extends Feature> featureClass);

    /**
     * Creates the specified {@link Feature}, but does not start it. If the feature is
     * a {@link DependencyScopeOwner} its {@link FeatureScope} is added to cache of
     * {@link DependencyScope}s.
     *
     * @param featureClass A {@link Feature}
     * @param params A {@link Params} containing parameters for the started {@link Feature}.
     * @return A {@link Feature}.
     */
    Feature createFeature(final Class<? extends Feature> featureClass, final Params params);

    /**
     * Creates and starts the specified {@link Feature} whose {@link android.app.Fragment}s are hosted by
     * the given {@link FeatureContainer}.
     *
     * @param featureClass A {@link Class} specifying the {@link Feature} to be created and started.
     * @return A {@link Feature}.
     */
    Feature startFeature(final Class<? extends Feature> featureClass);

    /**
     * Creates and starts the specified {@link Feature} whose {@link android.app.Fragment}s are hosted by
     * the given {@link FeatureContainer}.
     *
     * @param featureClass A {@link Class} specifying the {@link Feature} to be created and started.
     * @param params    A {@link Params} containing parameters for the created and started {@link Feature}.
     * @return A {@link Feature}.
     */
    Feature startFeature(final Class<? extends Feature> featureClass, final Params params);

    /**
     * Starts the given {@link Feature}.
     *
     * @param feature   {The {@link Feature} to be started.
     * @return A {@link Feature}.
     */
    Feature startFeature(final Feature feature);

    /**
     * Starts the given {@link Feature}.
     *
     * @param feature   {The {@link Feature} to be started.
     * @param params A {@link Params} containing parameters for the started {@link Feature}.
     * @return A {@link Feature}.
     */
    Feature startFeature(final Feature feature, final Params params);

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
    void onFeatureResumed(final Feature feature);
    /**
     * Invoked by a {@link Feature#pause()} when the {@link Feature}} has been paused.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    void onFeaturePaused(final Feature feature);

    /**
     * Invoked by a {@link Feature#stop()} when the {@link Feature}} has been stopped.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    void onFeatureStopped(final Feature feature);

    /**
     * Invoked by a {@link Feature#destroy()} when the {@link Feature}} has been destroyed.
     *
     * @param feature A {@link Feature}. May not be {@code null}.
     */
    void onFeatureDestroyed(final Feature feature);
}
