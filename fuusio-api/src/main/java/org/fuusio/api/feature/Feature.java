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

import android.support.v4.app.FragmentManager;

import org.fuusio.api.dependency.DependencyScopeOwner;
import org.fuusio.api.mvp.PresenterListener;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.PluginStateComponent;

import java.util.List;

/**
 * {@link Feature} defines an interface for components that implement some application feature
 * as a component. A concrete {@link Feature} implementation may implement logic for UI navigation
 * and UI flow logic.<b></b> A {@link Feature}  is also a {@link DependencyScopeOwner} that provides
 * a {@link FeatureScope} for prociding dependencies. Concrete implementations of {@link Feature}
 * has to be annotated with {@link AppFeature} to enable annotation processor based code generation.
 */
public interface Feature extends PresenterListener, PluginStateComponent {

    /**
     * Gets the currently active Views.
     *
     * @return A {@link List} containing the currently active views as {@link View}s.
     */
    List<View> getActiveViews();

    /**
     * Adds the given {@link View} to the set of active views. This method is intended to be invoked
     * by the framework.
     *
     * @param view A {@link View} to be added.
     * @return The given {@link View} if it was not already in the set of active views.
     */
    View addActiveView(View view);

    /**
     * Removes the given {@link View} from the set of active views. This method is intended to be invoked
     * by the framework.
     *
     * @param view A {@link View} to be removed.
     * @return The given {@link View} if it was included in the set of active views.
     */
    View removeActiveView(View view);

    /**
     * A {@link Feature} implementation can use this method to activate i.e. to make visible
     * the given {@link View}.
     *
     * @param view A {@link View} to be activated. May not be {@link null}.
     * @return A {@code boolean} for the activated {@link View} was actually activated.
     */
    boolean activateView(View view);

    /**
     * Tests if the given {@link View} is currently active one.
     *
     * @param view A {@link View}.
     * @return A {@code boolean} value.
     */
    boolean isActiveView(View view);

    /**
     * Gets the {@link FeatureManager} that started this {@link Feature}.
     *
     * @return A {@link FeatureManager}.
     */
    FeatureManager getFeatureManager();

    /**
     * Sets the {@link FeatureManager} that started this {@link Feature}.
     *
     * @param manager A {@link FeatureManager}.
     */
    void setFeatureManager(FeatureManager manager);

    /**
     * Tests if any of the {@link FeatureView}s of this {@link Feature} is in foreground and has a focus.
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
}
