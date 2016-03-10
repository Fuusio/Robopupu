/*
 * Copyright (C) 2000-2015 Marko Salmela.
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

import java.util.ArrayList;

/**
 * {@link PlugInvoker} provides an abstract base class for implementing plugin interface specific
 * invocation handlers.
 */
public abstract class PlugInvoker<T> {

    protected final ArrayList<T> mPlugins;

    protected PlugInvoker() {
        mPlugins = new ArrayList<>();
    }

    public final T object() {
        return mPlugins.get(0);
    }

    public final T get(final int index) {
        return mPlugins.get(index);
    }

    public boolean hasPlugins() {
        return !mPlugins.isEmpty();
    }

    public ArrayList<T> getPlugins() {
        return mPlugins;
    }

    public final int last() {
        return mPlugins.size() - 1;
    }

    public int getPluginsCount() {
        return mPlugins.size();
    }

    @SuppressWarnings("unchecked")
    public void addPlugin(final Object plugin) {
        if (!mPlugins.contains((T)plugin)) {
            mPlugins.add((T)plugin);
        }
    }

    @SuppressWarnings("unchecked")
    public void removePlugin(final Object plugin) {
        if (mPlugins.contains((T)plugin)) {
            mPlugins.remove((T)plugin);
        } else {

            HandlerInvoker<?> invokerToBeRemoved = null;

            for (final Object pluggedPlugin : mPlugins) {
                if (pluggedPlugin instanceof HandlerInvoker) {
                    final HandlerInvoker<?> invoker = (HandlerInvoker<?>)pluggedPlugin;

                    if (plugin == invoker.mPlugin) {
                        invokerToBeRemoved = invoker;
                        break;
                    }
                }
            }

            if (invokerToBeRemoved != null) {
                invokerToBeRemoved.mPlugin = null;
                mPlugins.remove((T)invokerToBeRemoved);
            }
        }
    }
}
