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

import com.robopupu.api.mvp.View;

/**
 * {@link FeatureTransitionManager} defines an interface for objects that can be used to show
 * {@link FeatureView}s. A typically class implementing {@link FeatureTransitionManager} is
 * an {@link Activity}. It is recommended to use either {@link com.robopupu.api.mvp.PluginActivity}
 * or {@link com.robopupu.api.mvp.PluginCompatActivity} as a base class for implementing
 * applications Activities.
 */
public interface FeatureTransitionManager {

    /**
     * Tests if the given {@link FeatureView} can be shown by this {@link FeatureTransitionManager}.
     *
     * @param view A {@link FeatureView}.
     * @return A {@code boolean}.
     */
    boolean canShowView(FeatureView view);

    /**
     * Shows the given {@link FeatureView}. If parameter {@code fragmentTag} is given {@code null}
     * value, implementation of this method should use the {@link View#getViewTag()} method
     * to obtain the tag.
     *
     * @param featureView A {@link FeatureView}. May not be {@code null}.
     * @param addToBackStack A {@code boolean} value specifying if the {@link FeatureView} is added
     *                       to back stack.
     * @param fragmentTag A tag for the {@code Fragment}. May be {@code null}.
     */
    void showView(FeatureView featureView, boolean addToBackStack, String fragmentTag);

    /**
     * Removes the given {@link FeatureView} from its container.
     *
     * @param featureView A {@link FeatureView}. May not be {@code null}.
     * @param addedToBackstack A {@code boolean} value specifying if the {@link FeatureView} was
     *                            added to back stack.
     * @param fragmentTag The tag that was used in adding the {@code Fragment}. May be {@code null},
     *                    if no tag was given when adding the {@code Fragment}.
     */
    void removeView(FeatureView featureView, boolean addedToBackstack, String fragmentTag);
}
