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

import com.robopupu.api.dependency.Scopeable;

/**
 * {@link PluginComponent} extends {@link Scopeable} to define an interface for plugin components
 * that can be plugged into {@link PluginBus}.
 */
public interface PluginComponent {

    /**
     * Invoked when this {@link PluginComponent} has been plugged to given {@link PluginBus}.
     * @param bus A {@link PluginBus}.
     */
    void onPlugged(PluginBus bus);

    /**
     * Invoked when this {@link PluginComponent} has been unplugged from given {@link PluginBus}.
     * @param bus A {@link PluginBus}.
     */
    void onUnplugged(PluginBus bus);

    /**
     * Invoked when the given plugin object has been plugged to {@link PluginBus}.
     * @param plugin A plugin as a {@link Object}.
     */
    void onPluginPlugged(Object plugin);

    /**
     * Invoked when the given plugin object has been unplugged from {@link PluginBus}.
     * @param plugin A plugin as a {@link Object}.
     */
    void onPluginUnplugged(Object plugin);
}
