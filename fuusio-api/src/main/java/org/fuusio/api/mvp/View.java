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
package org.fuusio.api.mvp;

import android.os.Bundle;

import org.fuusio.api.dependency.DependencyMap;

/**
 * {@link View} is the interface to be implemented by View components of a MVP
 * architectural pattern implementation.
 */
public interface View {

    String KEY_DEPENDENCY_SCOPE = "Key.Dependency.Scope";

    /**
     * Gets the {@link ViewState} of this {@link View}.
     *
     * @return An {@link ViewState}.
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

    /**
     * {@link org.fuusio.api.mvp.View.Listener} a listener interface for receiving lifecycle events
     * from a {@link View}. This interface is mainly intended to be used by an attached {@link Presenter}.
     */
    interface Listener {

        /**
         * Invoked by a {@link View} implementation when it is created,
         * e.g. on {@link ViewFragment#onViewCreated(android.view.View, Bundle)}.
         *
         * @param view    A {@link View}
         * @param inState {@lin Bundle} containing the initial state.
         */
        void onViewCreated(View view, Bundle inState);

        /**
         * Invoked by a {@link View} implementation when it is resumed,
         * e.g. on {@link ViewFragment#onResume()}.
         *
         * @param view A {@link View}
         */
        void onViewResume(View view);

        /**
         * Invoked by a {@link View} implementation when it is paused,
         * e.g. on {@link ViewFragment#onPause()}.
         *
         * @param view A {@link View}
         */
        void onViewPause(View view);

        /**
         * Invoked by a {@link View} implementation when it is started,
         * e.g. on {@link ViewFragment#onStart()}.
         *
         * @param view A {@link View}
         */
        void onViewStart(View view);

        /**
         * Invoked by a {@link View} implementation when it is stopped,
         * e.g. on {@link ViewFragment#onStop()}.
         *
         * @param view A {@link View}
         */
        void onViewStop(View view);

        /**
         * Invoked by a {@link View} implementation when it is destroyed,
         * e.g. on {@link ViewFragment#onDestroy()}.
         *
         * @param view A {@link View}
         */
        void onViewDestroy(View view);
    }
}
