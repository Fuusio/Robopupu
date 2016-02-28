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

import org.fuusio.api.mvp.View;
import org.fuusio.api.mvp.ViewDialogFragment;

/**
 * {@link FeatureTransitionManager} defines an interface for objects that can be used to show {@link FeatureFragment}s.
 * Typically a such object is an {@link android.app.Activity}.
 */
public interface FeatureTransitionManager {

    /**
     * Tests if the given {@link View} can be shown by this {@link FeatureTransitionManager}.
     *
     * @param view A {@link View}.
     * @return A {@code boolean}.
     */
    boolean canShowView(View view);

    /**
     * Shows the given {@link FeatureFragment}. Each shown {@link FeatureFragment} instance has to
     * be supplied with a tag that can be used to retrieve the instance later on. If parameter
     * {@code fragmentTag} is given {@code null} value, the tag is set to be the class name of
     * the {@link FeatureFragment}.
     *
     * @param fragment    A {@link FeatureFragment}. May not be {@code null}.
     * @param fragmentTag If the {@link FeatureFragment} is to be added to back stack then
     *                    a {@link String} used as a tag to identify the {@link FeatureFragment}
     *                    has to be given. In most cases tag can be simply the class name of
     *                    the {@link FeatureFragment}. If {@code null} then the {@link FeatureFragment}
     *                    is not added to back stack
     */
    void showFragment(FeatureFragment fragment, String fragmentTag);

    /**
     * Shows the given {@link FeatureDialogFragment}. Each shown {@link FeatureDialogFragment}
     * instance has to be supplied with a tag that can be used to retrieve the instance later on.
     * If parameter {@code fragmentTag} is given {@code null} value, the tag is set to be the class name of
     * the {@link FeatureFragment}.
     *
     * @param dialogFragment    A {@link FeatureDialogFragment}. May not be {@code null}.
     * @param fragmentTag If the {@link FeatureDialogFragment} is to be added to back stack then
     *                    a {@link String} used as a tag to identify the {@link FeatureDialogFragment}
     *                    has to be given. In most cases tag can be simply the class name of
     *                    the {@link FeatureDialogFragment}. If {@code null} then
     *                    the {@link FeatureDialogFragment} is not added to back stack
     */
    void showDialogFragment(FeatureDialogFragment dialogFragment, String fragmentTag);
}
