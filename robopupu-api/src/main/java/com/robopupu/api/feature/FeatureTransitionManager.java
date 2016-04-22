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

/**
 * {@link FeatureTransitionManager} defines an interface for objects that can be used to show
 * {@link FeatureCompatFragment}s and {@link FeatureCompatDialogFragment}s. Typically a such object is
 * an {@link android.app.Activity}.
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
     * Shows the given {@link FeatureView}. Each shown {@link FeatureView} instance has to
     * be supplied with a tag that can be used to retrieve the instance later on. If parameter
     * {@code fragmentTag} is given {@code null} value, the tag is set to be the class name of
     * the {@link FeatureView}.
     *
     * @param featureView    A {@link FeatureView}. May not be {@code null}.
     * @param addToBackStack A {@code boolean} value specifying if the {@link FeatureView} is added
     *                       to back stack.
     * @param fragmentTag If the {@link FeatureView} is to be added to back stack then
     *                    a {@link String} used as a tag to identify the {@link FeatureView}
     *                    has to be given. In most cases tag can be simply the class name of
     *                    the {@link FeatureView}.
     */
    void showView(FeatureView featureView, boolean addToBackStack, String fragmentTag);
}
