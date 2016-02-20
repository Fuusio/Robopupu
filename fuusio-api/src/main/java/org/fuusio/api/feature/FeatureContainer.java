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

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * {@link FeatureContainer} extends {@link FeatureTransitionManager} to define
 * an interface for objects that can be used to show {@link FeatureFragment}s. Typically a such
 * object is an {@link android.app.Activity}.
 */
public interface FeatureContainer extends FeatureTransitionManager {

    /**
     * Gets the {@link Context} available for  {@link FeatureFragment}s
     *
     * @return A {@link Context}.
     */
    Context getContext();

    /**
     * Gets the {@link Resources} available for {@link FeatureFragment}s
     *
     * @return A {@link Resources}.
     */
    Resources getResources();

    /**
     * Gets the {@link FragmentManager} that manages {@link android.app.Fragment}s.
     *
     * @return A {@link FragmentManager}.
     */
    FragmentManager getSupportFragmentManager();

    /**
     * Tests if a {@link Fragment} can be committed. A {@link Fragment} cannot be committed
     * using {@link FragmentTransaction#commit()} after method {@link android.app.Activity#onSaveInstanceState(Bundle)}
     * has been invoked.
     *
     * @return A {@code boolean} value.
     */
    boolean canCommitFragment();
}
