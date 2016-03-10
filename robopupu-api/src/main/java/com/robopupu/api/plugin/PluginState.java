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
package com.robopupu.api.plugin;

import com.robopupu.api.util.LifecycleState;

/**
 * {@link PluginState} represents the current state of a {@link PluginStateComponent}.
 */
public class PluginState {

    private LifecycleState mLifecycleState;
    private boolean mRestarted;

    public PluginState() {
        mLifecycleState = LifecycleState.DORMANT;
        mRestarted = false;
    }

    /**
     * Gets the current {@link LifecycleState} of this {@link PluginStateComponent}.
     * @return A
     */
    public LifecycleState getLifecycleState() {
        return mLifecycleState;
    }

    /**
     * Tests if the {@link LifecycleState} is dormant. In dormant state the {@link PluginStateComponent}
     * is instantiated, but not initialised.
     * @return A {@code boolean} value.
     */
    public boolean isDormant() {
        return mLifecycleState.isDormant();
    }

    /**
     * Tests if the {@link LifecycleState} is created. In created state the {@link PluginStateComponent}
     * is initialised and is ready to be started.
     * @return A {@code boolean} value.
     */
    public boolean isCreated() {
        return mLifecycleState.isCreated();
    }

    /**
     * Tests if the {@link LifecycleState} is started or resumed. In started state the method
     * {@link PluginStateComponent#start()} and possibly {@link PluginStateComponent#resume()}
     * have been invoked. Method {@link PluginState#getLifecycleState()} can be used to determinate
     * the exact lifecycle state.
     * @return A {@code boolean} value.
     */
    public boolean isStarted() {
        return mLifecycleState.isStarted() || mLifecycleState.isResumed();
    }

    /**
     * Tests if the {@link LifecycleState} is resumed. In resumed state both the methods
     * {@link PluginStateComponent#start()} and {@link PluginStateComponent#resume()}
     * have been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isResumed() {
        return mLifecycleState.isResumed();
    }

    /**
     * Tests if the {@link LifecycleState} is paused. In paused state the method
     * {@link PluginStateComponent#pause()} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isPaused() {
        return mLifecycleState.isPaused();
    }

    /**
     * Tests if the {@link LifecycleState} is restarted. In restarted state the method
     * {@link PluginStateComponent#start()} and possibly {@link PluginStateComponent#resume()}
     * have been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isRestarted() {
        return isStarted() && mRestarted;
    }

    /**
     * Tests if the {@link LifecycleState} is stopped. In stopped state the method
     * {@link PluginStateComponent#stop()} and possibly {@link PluginStateComponent#destroy()}
     * have been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isStopped() {
        return mLifecycleState.isStopped() || mLifecycleState.isDestroyed();
    }

    /**
     * Tests if the {@link LifecycleState} is destroyed. In destroyed state the method
     * {@link PluginStateComponent#destroy()} has been invoked.
     * @return A {@code boolean} value.
     */
    public boolean isDestroyed() {
        return mLifecycleState.isDestroyed();
    }

    /**
     * Invoked the {@link PluginComponent} is created.
     */
    public void onCreate() {
        mLifecycleState = LifecycleState.CREATED;
    }

    /**
     * Invoked the {@link PluginComponent} is started.
     */
    public void onStart() {
        mLifecycleState = LifecycleState.STARTED;
    }

    /**
     * Invoked the {@link PluginComponent} is restarted.
     */
    public void onRestart() {
        mRestarted = true;
    }

    /**
     * Invoked the {@link PluginComponent} is paused.
     */
    public void onPause() {
        mLifecycleState = LifecycleState.PAUSED;
    }

    /**
     * Invoked the {@link PluginComponent} is resumed.
     */
    public void onResume() {
        mLifecycleState = LifecycleState.RESUMED;
    }

    /**
     * Invoked the {@link PluginComponent} is stopped.
     */
    public void onStop() {
        mLifecycleState = LifecycleState.STOPPED;
    }

    /**
     * Invoked the {@link PluginComponent} is destroyed.
     */
    public void onDestroy() {
        mLifecycleState = LifecycleState.DESTROYED;
    }
}
