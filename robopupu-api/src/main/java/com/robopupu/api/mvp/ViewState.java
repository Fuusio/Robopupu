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

    private final View mView;

    private boolean mInstanceStateSaved;
    private LifecycleState mLifecycleState;
    private boolean mMovedToBackground;
    private boolean mRestarted;

    public ViewState(final View view) {
        mView = view;
        mInstanceStateSaved = false;
        mLifecycleState = LifecycleState.DORMANT;
        mMovedToBackground = false;
        mRestarted = false;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView() {
        return (T)mView;
    }

    public void setInstanceStateSaved(final boolean saved) {
        mInstanceStateSaved = saved;
    }

    /**
     * Gets the current {@link LifecycleState} of this {@link ViewState}.
     * @return A {@link LifecycleState}.
     */
    public LifecycleState getLifecycleState() {
        return mLifecycleState;
    }

    /**
     * Tests if the {@link LifecycleState} is dormant. In dormant state the {@link View}
     * is either not instantiated or is instantiated but isnot created i.e. method
     * {@code Fragment#onCreate(Bundle)}/{@link Activity#onCreate(Bundle)} has not
     * been invoked yet.
     * @return A {@code boolean} value.
     */
    public boolean isDormant() {
        return mView == null || mLifecycleState.isDormant();
    }

    /**
     * Tests if the {@link LifecycleState} is created. In created state the {@link View}
     * method {@code Fragment#onCreate(Bundle)}/{@link Activity#onCreate(Bundle)} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isCreated() {
        return mLifecycleState.isCreated();
    }

    /**
     * Tests if the {@link LifecycleState} is started or resumed. In started state the method
     * {@code Fragment#onStart()}/{@link Activity#onStart()} and possibly {@code Fragment#onResume()}/{@link Activity#onResume()}
     * have been invoked. Method {@link ViewState#getLifecycleState()} can be used to determinate
     * the exact lifecycle state.
     * @return A {@code boolean} value.
     */
    public boolean isStarted() {
        return mLifecycleState.isStarted() || mLifecycleState.isResumed();
    }

    /**
     * Tests if the {@link LifecycleState} is resumed. In resumed state both the methods
     * {@code Fragment#onStart()}/{@link Activity#onStart()} and {@code Fragment#onResume()}/{@link Activity#onResume()}
     * have been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isResumed() {
        return mLifecycleState.isResumed();
    }

    /**
     * Tests if the {@link LifecycleState} is paused. In paused state the method
     * {@code Fragment#onPause()}/{@link Activity#onPause()} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isPaused() {
        return mLifecycleState.isPaused();
    }

    /**
     * Tests if the {@link LifecycleState} is restarted. In restarted state the method
     * {@link Activity#onRestart()} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isRestarted() {
        return isStarted() && mRestarted;
    }

    /**
     * Tests if the {@link LifecycleState} is stopped. In stopped state the method
     * {@code Fragment#onStop()}/{@link Activity#onStop()} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isStopped() {
        return mLifecycleState.isStopped() || mLifecycleState.isDestroyed();
    }

    /**
     * Tests if the {@link LifecycleState} is destroyed. In destroyed state the method
     * {@code Fragment#onDestroy()}/{@link Activity#onDestroy()} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isDestroyed() {
        return mLifecycleState.isDestroyed();
    }

    /**
     * Invoked when the {@link View} is created.
     */
    public void onCreate() {
        mLifecycleState = LifecycleState.CREATED;
    }

    /**
     * Invoked when the {@link View} is started.
     */
    public void onStart() {
        mLifecycleState = LifecycleState.STARTED;
    }

    /**
     * Invoked when the {@link View} is restarted.
     */
    public void onRestart() {
        mRestarted = true;
    }

    /**
     * Invoked when the {@link View} is paused.
     */
    public void onPause() {
        mLifecycleState = LifecycleState.PAUSED;
        mMovedToBackground = true;
    }

    /**
     * Invoked when the {@link View} is resumed.
     */
    public void onResume() {
        mLifecycleState = LifecycleState.RESUMED;
        mInstanceStateSaved = false;
        mMovedToBackground = false;
    }

    /**
     * Invoked when the {@link View} is stopped.
     */
    public void onStop() {
        mLifecycleState = LifecycleState.STOPPED;
    }

    /**
     * Invoked when the {@link View} is destroyed.
     */
    public void onDestroy() {
        mLifecycleState = LifecycleState.DESTROYED;
    }

    public boolean canCommitFragment() {
        return !mInstanceStateSaved;
    }

    public boolean isMovedToBackground() {
        return mMovedToBackground;
    }
}
