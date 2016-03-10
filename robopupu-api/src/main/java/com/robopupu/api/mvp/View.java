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
package com.robopupu.api.mvp;

import com.robopupu.api.dependency.DependencyMap;

/**
 * {@link View} is the interface to be implemented by View components of a MVP
 * architectural pattern implementation.
 */
public interface View {

    String KEY_DEPENDENCY_SCOPE = "Key.Dependency.Scope";

    /**
     * Gets the {@link ViewState} of this {@link View}.
     *
     * @return A {@link ViewState}.
     */
    ViewState getState();

    /**
     * Get a tag for this {@link View}.
     *
     * @return A tag as a {@link String}.
     */
    String getViewTag();

    /**
     * Test if this {@link View} has currently focus.
     * @return A {@code boolean}.
     */
    boolean hasFocus();

    /**
     * Return a {@link String} based key for accessing a {@link DependencyMap} assigned for this
     * {@link View}. The default implementation is to return the name of the {@link Class} of
     * a {@link View}.
     * @return A {@link String}. May not be empty string nor {@code null}.
     */
    String getDependenciesKey();
}
