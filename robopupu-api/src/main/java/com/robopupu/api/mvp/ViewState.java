/*
 * Copyright (C) 2015 Marko Salmela, http://robopupu.com
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

import android.app.Activity;
import android.os.Bundle;

import com.robopupu.api.util.LifecycleState;

/**
 * {@link ViewState} is an object that is used to represent the current lifecycle state of
 * a {@link View}.
 */
public class ViewState {

    private final View view;

    private boolean instanceStateSaved;
    private LifecycleState lifecycleState;
    private boolean movedToBackground;
    private boolean restarted;

    public ViewState(final View view) {
        this.view = view;
        instanceStateSaved = false;
        lifecycleState = LifecycleState.DORMANT;
        movedToBackground = false;
        restarted = false;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView() {
        return (T) view;
    }

    public void setInstanceStateSaved(final boolean saved) {
        instanceStateSaved = saved;
    }

    /**
     * Gets the current {@link LifecycleState} of this {@link ViewState}.
     * @return A {@link LifecycleState}.
     */
    public LifecycleState getLifecycleState() {
        return lifecycleState;
    }

    /**
     * Tests if the {@link LifecycleState} is dormant. In dormant state the {@link View}
     * is either not instantiated or is instantiated but isnot created i.e. method
     * {@code Fragment#onCreate(Bundle)}/{@link Activity#onCreate(Bundle)} has not
     * been invoked yet.
     * @return A {@code boolean} value.
     */
    public boolean isDormant() {
        return view == null || lifecycleState.isDormant();
    }

    /**
     * Tests if the {@link LifecycleState} is created. In created state the {@link View}
     * method {@code Fragment#onCreate(Bundle)}/{@link Activity#onCreate(Bundle)} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isCreated() {
        return lifecycleState.isCreated();
    }

    /**
     * Tests if the {@link LifecycleState} is started or resumed. In started state the method
     * {@code Fragment#onStart()}/{@link Activity#onStart()} and possibly {@code Fragment#onResume()}/{@link Activity#onResume()}
     * have been invoked. Method {@link ViewState#getLifecycleState()} can be used to determinate
     * the exact lifecycle state.
     * @return A {@code boolean} value.
     */
    public boolean isStarted() {
        return lifecycleState.isStarted() || lifecycleState.isResumed();
    }

    /**
     * Tests if the {@link LifecycleState} is resumed. In resumed state both the methods
     * {@code Fragment#onStart()}/{@link Activity#onStart()} and {@code Fragment#onResume()}/{@link Activity#onResume()}
     * have been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isResumed() {
        return lifecycleState.isResumed();
    }

    /**
     * Tests if the {@link LifecycleState} is paused. In paused state the method
     * {@code Fragment#onPause()}/{@link Activity#onPause()} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isPaused() {
        return lifecycleState.isPaused();
    }

    /**
     * Tests if the {@link LifecycleState} is restarted. In restarted state the method
     * {@link Activity#onRestart()} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isRestarted() {
        return isStarted() && restarted;
    }

    /**
     * Tests if the {@link LifecycleState} is stopped. In stopped state the method
     * {@code Fragment#onStop()}/{@link Activity#onStop()} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isStopped() {
        return lifecycleState.isStopped() || lifecycleState.isDestroyed();
    }

    /**
     * Tests if the {@link LifecycleState} is destroyed. In destroyed state the method
     * {@code Fragment#onDestroy()}/{@link Activity#onDestroy()} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isDestroyed() {
        return lifecycleState.isDestroyed();
    }

    /**
     * Invoked when the {@link View} is created.
     */
    public void onCreate() {
        lifecycleState = LifecycleState.CREATED;
    }

    /**
     * Invoked when the {@link View} is started.
     */
    public void onStart() {
        lifecycleState = LifecycleState.STARTED;
    }

    /**
     * Invoked when the {@link View} is restarted.
     */
    public void onRestart() {
        restarted = true;
    }

    /**
     * Invoked when the {@link View} is paused.
     */
    public void onPause() {
        lifecycleState = LifecycleState.PAUSED;
        movedToBackground = true;
    }

    /**
     * Invoked when the {@link View} is resumed.
     */
    public void onResume() {
        lifecycleState = LifecycleState.RESUMED;
        instanceStateSaved = false;
        movedToBackground = false;
    }

    /**
     * Invoked when the {@link View} is stopped.
     */
    public void onStop() {
        lifecycleState = LifecycleState.STOPPED;
    }

    /**
     * Invoked when the {@link View} is destroyed.
     */
    public void onDestroy() {
        lifecycleState = LifecycleState.DESTROYED;
    }

    public boolean canCommitFragment() {
        return !instanceStateSaved;
    }

    public boolean isMovedToBackground() {
        return movedToBackground;
    }
}
