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

import com.robopupu.api.dependency.D;
import com.robopupu.api.dependency.Dependency;
import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.dependency.DependencyScopeOwner;
import com.robopupu.api.dependency.Scopeable;

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

    /**
     * Gets the plugins having exactly the specified plug interface.
     * @param plugInterface A {@link Class} specifying the plug interface.
     * @return A {@link List} containing the plugins.
     */
    @SuppressWarnings({"unused", "unchecked"})
    public <T> List<T> getPlugins(final Class<T> plugInterface) {
        final PlugInvoker plug = mInvocationPlugs.get(plugInterface);

        if (plug != null) {
            return plug.getPlugins();
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Gets the plugs (i.e. {@link PlugInvoker}s) having exactly the specified plug interface.
     * @param plugInterface A {@link Class} specifying the plug interface.
     * @param includeExtendedInterfaces A {@link boolean} flag specifying if the extended interfaces
     *                                  are included.
     * @return A {@link List} containing the plugs (i.e. {@link PlugInvoker}s) .
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getPlugs(final Class<T> plugInterface, final boolean includeExtendedInterfaces) {
        final ArrayList<T> plugs = new ArrayList<>();

        if (includeExtendedInterfaces) {
            for (final Class key : mInvocationPlugs.keySet()) {
                if (plugInterface.isAssignableFrom(key)) {
                    plugs.add((T)mInvocationPlugs.get(key));
                }
            }
        } else {
            final PlugInvoker plug = mInvocationPlugs.get(plugInterface);

            if (plug != null) {
                plugs.add((T) plug);
            }
        }
        return plugs;
    }
    
    @SuppressWarnings({"unused", "unchecked"})
    public <T> T getPlug(final Class<?> plugInterface) {
        return (T) mInvocationPlugs.get(plugInterface);
    }

    @SuppressWarnings("unused")
    public boolean hasPlug(final Class<?> plugInterface) {
        return mInvocationPlugs.containsKey(plugInterface);
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

    public void addPlugInvoker(final Class<?> plugInterface, PlugInvoker<?> plugInvoker) {
        mInvocationPlugs.put(plugInterface, plugInvoker);
    }

    public boolean hasPlugInvoker(final Class<?> plugInterface) {
        return mInvocationPlugs.containsKey(plugInterface);
    }

    @SuppressWarnings("unchecked")
    public <T extends PlugInvoker<?>> T getPlugInvoker(final Class<?> plugInterface) {
        return (T)mInvocationPlugs.get(plugInterface);
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

    public static void plug(final Object plugin, final boolean useHandler) {
        getInstance().doPlug(plugin, useHandler);
    }

    @SuppressWarnings("unchecked")
    private void doPlug(final Object plugin, final boolean useHandler) {

        if (plugin instanceof PlugInvoker) {
            return;
        }

        if (mPlugins.contains(plugin)) {
            return;
        }

        final Plugger plugger = getPlugger(plugin.getClass());
        plugger.plug(plugin, this, useHandler);

        mPlugins.add(plugin);

        if (plugin instanceof PluginComponent) {
            final PluginComponent component = (PluginComponent) plugin;
            component.onPlugged(this);

            for (final PluginComponent pluggedComponent : mPluginComponents) {
                pluggedComponent.onPluginPlugged(plugin);
            }

            if (!mPluginComponents.contains(component)) {
                mPluginComponents.add(component);
            }
        }
    }

    private Plugger getPlugger(final Class<?> pluginClass) {
        final String pluggerClassName = pluginClass.getName() + SUFFIX_PLUGGER;
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
            mInvocationPlugs.put(plugInterface, plugInvoker);
        } else {
            plug = mInvocationPlugs.get(plugInterface);
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
            // The plugin is not plugged - just return
            return;
        }

        final String pluggerClassName = plugin.getClass().getName() + SUFFIX_PLUGGER;
        final Plugger plugger = mPluggers.get(pluggerClassName);

        plugger.unplug(plugin, this);

        mPlugins.remove(plugin);

        if (plugin instanceof PluginComponent) {
            final PluginComponent component = (PluginComponent) plugin;
            mPluginComponents.remove(component);
            component.onUnplugged(this);

            for (final PluginComponent pluggedComponent : mPluginComponents) {
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
        return getInstance().mPlugins.contains(object);
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
}
