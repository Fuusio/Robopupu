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
package org.fuusio.api.plugin;

import android.os.Handler;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependencyScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PluginBus {

    private static final String SUFFIX_PLUGGER = "_Plugger";

    private static PluginBus sInstance = null;

    private final HashMap<Class<?>, PlugInvoker> mInvocationPlugs;
    private final HashMap<String, Plugger> mPluggers;
    private final ArrayList<Object> mPlugins;
    private final ArrayList<PluginComponent> mPluginComponents;

    private PluginBus() {
        mInvocationPlugs = new HashMap<>();
        mPluggers = new HashMap<>();
        mPluginComponents = new ArrayList<>();
        mPlugins = new ArrayList<>();
    }

    @SuppressWarnings({"unused", "unchecked"})
    public List<Object> getPlugins(final Class<?> pluginInterface) {
        final PlugInvoker plug = mInvocationPlugs.get(pluginInterface);

        if (plug != null) {
            return plug.getPlugins();
        } else {
            return new ArrayList<>();
        }
    }

    @SuppressWarnings({"unused", "unchecked"})
    public <T> T getPlug(final Class<?> pluginInterface) {
        return (T) mInvocationPlugs.get(pluginInterface);
    }

    @SuppressWarnings("unused")
    public boolean hasPlug(final Class<?> pluginInterface) {
        return mInvocationPlugs.containsKey(pluginInterface);
    }

    @SuppressWarnings("unused")
    public List<PluginComponent> getPluginComponents() {
        return mPluginComponents;
    }

    public synchronized static PluginBus getInstance() {
        if (sInstance == null) {
            sInstance = new PluginBus();
        }
        return sInstance;
    }

    public void addPlugInvoker(final Class<?> pluginInterface, PlugInvoker<?> plugInvoker) {
        mInvocationPlugs.put(pluginInterface, plugInvoker);
    }

    public boolean hasPlugInvoker(final Class<?> pluginInterface) {
        return mInvocationPlugs.containsKey(pluginInterface);
    }

    @SuppressWarnings("unchecked")
    public <T extends PlugInvoker<?>> T getPlugInvoker(final Class<?> pluginInterface) {
        return (T)mInvocationPlugs.get(pluginInterface);
    }

    /**
     * Plugs an instance of specific {@link Class} to this {@link PluginBus}.
     * @param pluginClass A  {@link Class}
     * @return The plugged instance as an {@link Object}
     */
    @SuppressWarnings("unchecked")
    public static <T> T plug(final Class<?> pluginClass) {
        final Object plugin = D.getOrCreate(pluginClass);
        plug(plugin, false);
        return (T) plugin;
    }

    /**
     * Plugs an instance of specific {@link Class} to this {@link PluginBus}. The instance is obtained
     * using the given {@link DependencyScope}.
     * @param pluginClass A  {@link Class}
     * @param scope A {@link DependencyScope}.
     * @return The plugged instance as an {@link Object}
     */
    @SuppressWarnings("unchecked")
    public static <T> T plug(final Class<?> pluginClass, final DependencyScope scope) {
        final Object plugin = D.getOrCreate(scope, pluginClass);
        plug(plugin, false);

        if (plugin instanceof PluginInjector) {
            ((PluginInjector)plugin).setScope(scope);
        }
        return (T) plugin;
    }

    public static void plug(final Object plugin) {
        plug(plugin, false);
    }

    public static void plug(final Object plugin, final boolean useHandler) {
        getInstance().doPlug(plugin, useHandler);
    }

    @SuppressWarnings("unchecked")
    private void doPlug(final Object plugin, final boolean useHandler) {
        final String pluggerClassName = plugin.getClass().getName() + SUFFIX_PLUGGER;
        Plugger plugger = mPluggers.get(pluggerClassName);

        if (plugger == null) {
            try {
                final Class<? extends Plugger> pluggerClass = (Class<? extends Plugger>) Class.forName(pluggerClassName);
                plugger = pluggerClass.newInstance();
                mPluggers.put(pluggerClassName, plugger);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        plugger.plug(plugin, this, useHandler);
        mPlugins.add(plugin);

        if (plugin instanceof PluginComponent) {
            final PluginComponent component = (PluginComponent)plugin;
            component.onPlugged(this);

            for (final PluginComponent pluggedComponent : mPluginComponents) {
                //pluggedComponent.onComponentPlugged(pluggedComponent);
                pluggedComponent.onPluginPlugged(plugin);
            }

            if (!mPluginComponents.contains(component)) {
                mPluginComponents.add(component);
            }

            if (plugin instanceof PluginStateComponent) {
                final PluginStateComponent stateComponent = (PluginStateComponent)component;
                stateComponent.start();
            }
        }
    }

    /**
     * This framework method should not be used by developers directly.
     * @param plugin The plugin to be plugged as an {@link Object}.
     * @param pluginInterface A {@link Class} specifying the plugin interface type.
     * @param plugInvoker A {@link PlugInvoker} instance. May be {@code null} if an instance of needed
     *                    type of {@link PlugInvoker} is already cached in this {@link PluginBus}.
     * @param handlerInvoker A {@link HandlerInvoker} instance. May be {@code null} if a {@link Handler}
     *                       is not needed for synchronising invocations with the main thread.
     */
    public void plug(final Object plugin, final Class<?> pluginInterface, final PlugInvoker<?> plugInvoker, final HandlerInvoker<?> handlerInvoker) {

        PlugInvoker plug = plugInvoker;

        if (plugInvoker != null) {
            mInvocationPlugs.put(pluginInterface, plugInvoker);
        } else {
            plug = mInvocationPlugs.get(pluginInterface);
        }

        if (handlerInvoker != null) {
            plug.addPlugin(handlerInvoker);
        } else {
            plug.addPlugin(plugin);
        }
    }

    public static void unplug(final Object plugin) {
        getInstance().doUnplug(plugin);
    }

    @SuppressWarnings("static-access")
    private void doUnplug(final Object plugin) {

        if (!mPlugins.contains(plugin)) {
            return;
        }

        final String pluggerClassName = plugin.getClass().getName() + SUFFIX_PLUGGER;
        final Plugger plugger = mPluggers.get(pluggerClassName);

        plugger.unplug(plugin, this);

// TODO

        mPlugins.remove(plugin);

        if (plugin instanceof PluginComponent) {
            final PluginComponent component = (PluginComponent) plugin;
            component.onUnplugged(this);

            for (final PluginComponent pluggedComponent : mPluginComponents) {
                //pluggedComponent.onComponentUnplugged(pluggedComponent);
                pluggedComponent.onPluginUnplugged(plugin);
            }

            mPluginComponents.remove(component);

            if (plugin instanceof PluginStateComponent) {
                final PluginStateComponent stateComponent = (PluginStateComponent)component;
                stateComponent.destroy();
            }
        }
    }
}
