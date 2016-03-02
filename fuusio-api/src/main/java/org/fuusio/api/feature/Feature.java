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
import android.support.v4.app.FragmentManager;

import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.DependencyScopeOwner;
import org.fuusio.api.mvp.PresenterListener;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.PluginStateComponent;

import java.util.List;

/**
 * {@link Feature} defines an interface for {@link PluginStateComponent}s that implement of
 * application feature as a component. A concrete {@link Feature} implementation may implement logic
 * for UI navigation and UI flow logic.<b></b> A {@link Feature}  is also
 * a {@link DependencyScopeOwner} that provides a {@link DependencyScope} for providing dependencies.
 */
public interface Feature extends PresenterListener, PluginStateComponent {

    /**
     * Gets the currently active {@link View}s.
     *
     * @return A {@link List} containing the currently active views as {@link View}s.
     */
    List<View> getActiveViews();

    /**
     * Tests if the given {@link View} is currently active one.
     *
     * @param view A {@link View}.
     * @return A {@code boolean} value.
     */
    boolean isActiveView(View view);

    /**
     * Sets the {@link FeatureManager} that started this {@link Feature}.
     *
     * @param manager A {@link FeatureManager}.
     */
    void setFeatureManager(FeatureManager manager);

    /**
     * Gets the {@link FeatureContainer} that hosts the {@link FeatureFragment}s of this
     * {@link Feature}.
     *
     * @return A {@link FeatureContainer}.
     */
    FeatureContainer getFeatureContainer();

    /**
     * Sets the the {@link FeatureContainer} that hosts the {@link FeatureFragment}s of this
     * {@link Feature}.
     *
     * @param container A {@link FeatureContainer}.
     */
    void setFeatureContainer(FeatureContainer container);

    /**
     * Sets this {@link Feature} to be an Activity Feature that is owned and controlled by
     * an {@link Activity}.
     * @param isActivityFeature A {@code boolean} value.
     */
    void setActivityFeature(boolean isActivityFeature);

    /**
     * Tests if this {@link Feature} is set to be an Activity Feature that is owned and controlled
     * by an {@link Activity}.
     * @return  A {@code boolean} value.
     */
    boolean isActivityFeature();

    /**
     * Tests if any of the {@link View}s of this {@link Feature} is in foreground and has a focus.
     * @return A {@code boolean}.
     */
    boolean hasFocusedView();

    /**
     * Tests if this {@link Feature} is in foreground i.e. it has at least one visible
     * {@link View}.
     *
     * @return A {@code boolean} value.
     */
    boolean hasForegroundView();

    /**
     * Tests if this {@link Feature} handles the back pressed event.
     *
     * @return A {@code boolean} value.
     */
    boolean isBackPressedEventHandler();

    /**
     * Tests if this {@link Feature} has any {@link View}s in back stack.
     * @return A {@code boolean} value.
     */
    boolean hasViewsInBackStack();

    /**
     * Clears the back stack managed by {@link FragmentManager}.
     */
    void clearBackStack();

    /**
     * Tests if the previous {@link View} can be navigated back to.
     *
     * @return A {@code boolean} value.
     */
    boolean canGoBack();

    /**
     * Goes back to previous {@link View}.
     */
    void goBack();

    /**
     * Goes back to previous {@link View} using the given {@link FeatureTransitionManager}.
     * @param transitionManager A {@link FeatureTransitionManager}.
     */
    void goBack(FeatureTransitionManager transitionManager);

    /**
     * Finishes this {@link Feature}.
     */
    void finish();

    /**
     * Invoked when the {@link FeatureContainer} has been started.
     */
    void onFeatureContainerStarted(FeatureContainer container);

    /**
     * Invoked when the {@link FeatureContainer} has been paused.
     */
    void onFeatureContainerPaused(FeatureContainer container);

    /**
     * Invoked when the {@link FeatureContainer} has been resumed.
     */
    void onFeatureContainerResumed(FeatureContainer container);

    /**
     * Invoked when the {@link FeatureContainer} has been stopped.
     */
    void onFeatureContainerStopped(FeatureContainer container);
}
