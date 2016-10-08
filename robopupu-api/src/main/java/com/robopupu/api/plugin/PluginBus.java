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

import android.util.Log;

import com.robopupu.api.dependency.D;
import com.robopupu.api.dependency.Dependency;
import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.dependency.DependencyScopeOwner;
import com.robopupu.api.dependency.Scopeable;
import com.robopupu.api.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PluginBus {

    private static final String TAG = Utils.tag(PluginBus.class);

    private static final String SUFFIX_PLUGGER = "_Plugger";

    private static PluginBus instance = null;

    private final HashMap<Class<?>, PlugInvoker> invocationPlugs;
    private final HashMap<String, Plugger> pluggers;
    private final ArrayList<Object> plugins;
    private final ArrayList<PluginComponent> pluginComponents;

    private PluginBus() {
        invocationPlugs = new HashMap<>();
        pluggers = new HashMap<>();
        pluginComponents = new ArrayList<>();
        plugins = new ArrayList<>();
    }

    /**
     * Gets the plugins having exactly the specified plug interface.
     * @param plugInterface A {@link Class} specifying the plug interface.
     * @return A {@link List} containing the plugins.
     */
    @SuppressWarnings({"unused", "unchecked"})
    public static <T> List<T> getPlugins(final Class<T> plugInterface) {
        final PlugInvoker plug = getInstance().invocationPlugs.get(plugInterface);

        if (plug != null) {
            return plug.getPlugins();
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Gets the plugs (i.e. {@link PlugInvoker}s) having exactly the specified plug interface.
     * Extended interfaces are included.
     * @param plugInterface A {@link Class} specifying the plug interface.
     * @return A {@link List} containing the plugs (i.e. {@link PlugInvoker}s) .
     */
    public static <T> List<T> getPlugs(final Class<T> plugInterface) {
        return getInstance().doGetPlugs(plugInterface, true);
    }

    /**
     * Gets the plugs (i.e. {@link PlugInvoker}s) having exactly the specified plug interface.
     * @param plugInterface A {@link Class} specifying the plug interface.
     * @param includeExtendedInterfaces A {@link boolean} flag specifying if the extended interfaces
     *                                  are included.
     * @return A {@link List} containing the plugs (i.e. {@link PlugInvoker}s) .
     */
    public static <T> List<T> getPlugs(final Class<T> plugInterface, final boolean includeExtendedInterfaces) {
        return getInstance().doGetPlugs(plugInterface, includeExtendedInterfaces);
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> doGetPlugs(final Class<T> plugInterface, final boolean includeExtendedInterfaces) {
        final ArrayList<T> plugs = new ArrayList<>();

        if (includeExtendedInterfaces) {
            for (final Class key : invocationPlugs.keySet()) {
                if (plugInterface.isAssignableFrom(key)) {
                    plugs.add((T) invocationPlugs.get(key));
                }
            }
        } else {
            final PlugInvoker plug = invocationPlugs.get(plugInterface);

            if (plug != null) {
                plugs.add((T) plug);
            }
        }
        return plugs;
    }
    
    @SuppressWarnings({"unused", "unchecked"})
    public static <T> T getPlug(final Class<?> plugInterface) {
        return (T) getInstance().invocationPlugs.get(plugInterface);
    }

    @SuppressWarnings("unused")
    public static boolean hasPlug(final Class<?> plugInterface) {
        return getInstance().invocationPlugs.containsKey(plugInterface);
    }

    @SuppressWarnings("unused")
    public static List<PluginComponent> getPluginComponents() {
        return getInstance().pluginComponents;
    }

    public synchronized static PluginBus getInstance() {
        if (instance == null) {
            instance = new PluginBus();
        }
        return instance;
    }

    public static void addPlugInvoker(final Class<?> plugInterface, PlugInvoker<?> plugInvoker) {
        getInstance().invocationPlugs.put(plugInterface, plugInvoker);
    }

    public static boolean hasPlugInvoker(final Class<?> plugInterface) {
        return getInstance().invocationPlugs.containsKey(plugInterface);
    }

    @SuppressWarnings("unchecked")
    public static <T extends PlugInvoker<?>> T getPlugInvoker(final Class<?> plugInterface) {
        return (T)getInstance().invocationPlugs.get(plugInterface);
    }

    /**
     * Plugs an instance of specific {@link Class} to this {@link PluginBus}.
     * @param pluginClass A  {@link Class}
     * @return The plugged instance as an {@link Object}
     */
    @SuppressWarnings("unchecked")
    public static <T> T plug(final Class<?> pluginClass) {
        final Object plugin = D.get(pluginClass);
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
        final Object plugin = D.get(scope, pluginClass);
        plug(plugin, false);
        return (T) plugin;
    }

    /**
     * Plugs the the given plugin {@link Object} to this {@link PluginBus}.
     * @param plugin A plugin {@link Object}.
     */
    public static void plug(final Object plugin) {
        plug(plugin, false);
    }

    /**
     * Plugs the the given plugin {@link Object}s to this {@link PluginBus}.
     * @param plugins A variable length list plugin {@link Object}s.
     */
    public static void plug(final Object... plugins) {
        for (final Object plugin : plugins) {
            plug(plugin, false);
        }
    }

    /**
     * Plugs the the given plugin {@link Object}s to this {@link PluginBus}.
     * @param plugins A {@link List} containing  the plugin {@link Object}s to be plugged.
     */
    public static void plug(final List<Object> plugins) {
        for (final Object plugin : plugins) {
            plug(plugin, false);
        }
    }

    public static void plug(final Object plugin, final boolean useHandler) {
        getInstance().doPlug(plugin, useHandler);
    }

    @SuppressWarnings("unchecked")
    private void doPlug(final Object plugin, final boolean useHandler) {

        if (plugin instanceof PlugInvoker) {
            return;
        }

        final boolean alreadyPlugged = plugins.contains(plugin);

        if (alreadyPlugged) {
            Log.d(TAG, "doPlug(...) : PluginBus already contained: " + plugin);
        }

        final Plugger plugger = getPlugger(plugin.getClass());
        Log.d(TAG, "doPlug(...) : Using plugger: " + plugger);
        plugger.plug(plugin, this, useHandler);

        if (!alreadyPlugged) {
            plugins.add(plugin);
        }

        if (plugin instanceof PluginComponent) {
            final PluginComponent component = (PluginComponent) plugin;
            component.onPlugged(this);

            for (final PluginComponent pluggedComponent : pluginComponents) {
                pluggedComponent.onPluginPlugged(plugin);
            }

            if (!pluginComponents.contains(component)) {
                pluginComponents.add(component);
            }
        }
    }

    public static void injectPlugs(final Object plugin) {
        injectPlugs(plugin, false);
    }

    public static void injectPlugs(final Object plugin, final boolean useHandler) {
        getInstance().doInjectPlugs(plugin, useHandler);
    }

    @SuppressWarnings("unchecked")
    private void doInjectPlugs(final Object plugin, final boolean useHandler) {

        if (plugin instanceof PlugInvoker) {
            return;
        }

        final Plugger plugger = getPlugger(plugin.getClass());
        plugger.plug(plugin, this, useHandler);
    }

    @SuppressWarnings("unchecked")
    private Plugger getPlugger(final Class<?> pluginClass) {
        final String pluggerClassName = pluginClass.getName() + SUFFIX_PLUGGER;
        Plugger plugger = pluggers.get(pluggerClassName);

        if (plugger == null) {
            try {
                final Class<? extends Plugger> pluggerClass = (Class<? extends Plugger>) Class.forName(pluggerClassName);
                plugger = pluggerClass.newInstance();
                pluggers.put(pluggerClassName, plugger);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return plugger;
    }

    /**
     * This framework method should not be used by developers directly.
     * @param plugin The plugin to be plugged as an {@link Object}.
     * @param plugInterface A {@link Class} specifying the plugin interface type.
     * @param plugInvoker A {@link PlugInvoker} instance. May be {@code null} if an instance of needed
     *                    type of {@link PlugInvoker} is already cached in this {@link PluginBus}.
     * @param handlerInvoker A {@link HandlerInvoker} instance. May be {@code null} if there
     *                       is no needed for synchronising invocations with the main thread.
     */
    public void plug(final Object plugin, final Class<?> plugInterface, final PlugInvoker<?> plugInvoker, final HandlerInvoker<?> handlerInvoker) {

        PlugInvoker plug = plugInvoker;

        if (plugInvoker != null) {
            invocationPlugs.put(plugInterface, plugInvoker);
        } else {
            plug = invocationPlugs.get(plugInterface);
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

        if (!plugins.contains(plugin)) {
            // The plugin is not plugged - just return
            return;
        }

        final String pluggerClassName = plugin.getClass().getName() + SUFFIX_PLUGGER;
        final Plugger plugger = pluggers.get(pluggerClassName);

        plugger.unplug(plugin, this);

        plugins.remove(plugin);

        if (plugin instanceof PluginComponent) {
            final PluginComponent component = (PluginComponent) plugin;
            pluginComponents.remove(component);
            component.onUnplugged(this);

            for (final PluginComponent pluggedComponent : pluginComponents) {
                pluggedComponent.onPluginUnplugged(plugin);
            }

            if (plugin instanceof PluginStateComponent) {
                final PluginStateComponent stateComponent = (PluginStateComponent)component;
                stateComponent.stop();
            }
        }

        if (plugin instanceof DependencyScopeOwner) {
            final DependencyScopeOwner owner = (DependencyScopeOwner)plugin;
            Dependency.disposeScope(owner);
        }

        if (plugin instanceof Scopeable) {
            final Scopeable scopeable = (Scopeable) plugin;
            final DependencyScope scope = scopeable.getScope();

            if (scope != null) {
                scope.removeDependency(plugin);
            }
        }
    }

    /**
     * Tests if the given {@link Object} is currently plugged as a plugin into this {@link PluginBus}.
     * @param object An {@link Object}.
     * @return A {@code boolean} value.
     */
    public static boolean isPlugged(final Object object) {
        return getInstance().plugins.contains(object);
    }

    /**
     * Test if a method can be invoked on the given target {@link Object}. The target may not be
     * {@code null}, and in case of {@link PlugInvoker} it has to contain plugins.
     * @param target An {@link Object}.
     * @return A {@code boolean} value.
     */
    public static boolean canInvoke(final Object target) {
        if (target != null) {
            if (target instanceof PlugInvoker) {
                return ((PlugInvoker)target).hasPlugins();
            }
            return true;
        }
        return false;
    }

    /**
     * Tests if the specified class represents a plugin object.
     * @param pluginClass A {@link Class}.
     * @return A {@code boolean} value.
     */
    public static boolean isPlugin(final Class<?> pluginClass) {
        return (getInstance().getPlugger(pluginClass) != null);
    }

    /**
     * Gets the {@link Object} that is attached to given plug.
     * @param plug A plug as an {@link Object}
     * @return An {@link Object}.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPluggedObject(final Object plug) {
        if (plug instanceof PlugInvoker) {
            return ((PlugInvoker<T>)plug).get(0);
        } else {
            return (T)plug;
        }
    }
}
