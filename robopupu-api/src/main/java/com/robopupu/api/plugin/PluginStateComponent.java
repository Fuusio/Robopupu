/*
 * Copyright (C) 2000-2014 Marko Salmela.
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
import com.robopupu.api.util.Params;

/**
 * {@link PluginStateComponent} extends {@link PluginComponent} to define an interface for plugin
 * components that maintain {@link PluginState}.
 */
public interface PluginStateComponent extends PluginComponent {

    /**
     * Gets the current {@link LifecycleState} of this {@link PluginStateComponent}.
     * @return A {@link LifecycleState}.
     */
    LifecycleState getLifecycleState();

    /**
     * Gets the current state of this {@link PluginStateComponent}.
     * @return The state as a {@link PluginState}.
     */
    PluginState getState();

    /**
     * Invoked to pause this {@link PluginStateComponent}.
     */
    void pause();

    /**
     * Invoked to resume this {@link PluginStateComponent}.
     */
    void resume();

    /**
     * Invoked to restart this {@link PluginStateComponent}.
     */
    void restart();

    /**
     * Invoked to start this {@link PluginStateComponent} without parameters.
     */
    void start();

    /**
     * Invoked to start the {@link PluginStateComponent} using the given parameters.
     * @param params A {@link Params}. May not be {@link null}.
     */
    void start(Params params);

    /**
     * Invoked to set the state of {@link PluginStateComponent} to be stopped. A stopped
     * {@link PluginStateComponent} is unplugged from {@link PluginBus}.
     */
    void stop();

    /**
     * Invoked to set the state of {@link PluginStateComponent} to be destroyed. This method is
     * automatically invoked for a stopped {@link PluginStateComponent}.
     */
    void destroy();

    /**
     * Tests if paused.
     * @return A {@code boolean}.
     */
    boolean isPaused();

    /**
     * Tests if restarted.
     * @return A {@code boolean}.
     */
    boolean isRestarted();

    /**
     * Tests if paused.
     * @return A {@code boolean}.
     */
    boolean isResumed();

    /**
     * Tests if started.
     * @return A {@code boolean}.
     */
    boolean isStarted();

    /**
     * Tests if stopped.
     * @return A {@code boolean}.
     */
    boolean isStopped();

    /**
     * Tests if destroyed.
     * @return A {@code boolean}.
     */
    boolean isDestroyed();
}
