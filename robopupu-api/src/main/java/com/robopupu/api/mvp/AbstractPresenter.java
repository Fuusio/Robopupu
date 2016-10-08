/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://robopupu.com
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

import android.support.annotation.CallSuper;

import com.robopupu.api.dependency.D;
import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.dependency.Scopeable;
import com.robopupu.api.plugin.AbstractPluginStateComponent;
import com.robopupu.api.plugin.PlugInvoker;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.api.util.AbstractListenable;
import com.robopupu.api.util.Log;
import com.robopupu.api.util.Params;
import com.robopupu.api.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AbstractPresenter} extends {@link AbstractListenable} to provide an abstract base class
 * for concrete implementations of {@link Presenter}s.
 */
public abstract class AbstractPresenter<T_View extends View> extends AbstractPluginStateComponent
        implements Presenter, Scopeable {

    private static final String TAG = Utils.tag(AbstractPresenter.class);

    protected final List<PresenterListener> listeners;

    private Params params;
    private DependencyScope scope;

    protected AbstractPresenter() {
        listeners = new ArrayList<>();
    }

    public Params getParams() {
        return params;
    }

    @Override
    public void setParams(final Params params) {
        this.params = params;
    }

    /**
     * Gets the {@link View} attached to this {@link AbstractPresenter}.
     *
     * @return A {@link View}.
     */
    @SuppressWarnings("unchecked")
    protected T_View getAttachedView() {
        return PluginBus.getPluggedObject(getViewPlug());
    }

    /**
     * Gets the {@link PresenterListener}s.
     *
     * @return A {@link List} containing the {@link PresenterListener}s.
     */
    protected List<PresenterListener> getListeners() {
        final ArrayList<PresenterListener> listeners = new ArrayList<>();
        listeners.addAll(this.listeners);
        return listeners;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView() {
        return getAttachedView();
    }

    /**
     * Gets the {@link PlugInvoker} for the {@link View}attached to this {@link Presenter}.
     *
     * @return The {@link PlugInvoker} as a {@link View}.
     */
    protected abstract T_View getViewPlug();

    /**
     * Gets the {@link ViewState} of the attached {@link View}.
     * @return A {@link ViewState}.
     */
    protected ViewState getViewState() {
        final View view = getAttachedView();

        if (view != null) {
            return view.getState();
        }
        return new ViewState(null);
    }

    /**
     * Tests if this {@link AbstractPresenter} has an attached {@link View}.
     * @return A {@code boolean}.
     */
    protected boolean isAttached() {
        final View view = getView();

        if (view != null) {
            if (view instanceof PlugInvoker) {
                return ((PlugInvoker)view).hasPlugins();
            }
            return true;
         }
        return false;
    }

    @Override
    public void finish() {
        Log.d(TAG, "finish()");

        // Listeners need to be notified before unregistering this
        // AbstractPresenter from PluginBus
        for (final PresenterListener listener : getListeners()) {
            listener.onPresenterFinished(this);
        }
        stop();
        PluginBus.unplug(this);
    }

    /**
     * Plugs an instance of specific {@link Class} to {@link PluginBus}.
     * @param pluginClass A  {@link Class}
     * @return The plugged instance as an {@link Object}
     */
    @SuppressWarnings("unchecked")
    public <T> T plug(final Class<?> pluginClass) {
        T plugin = (T) D.get(getScope(), pluginClass);
        PluginBus.plug(plugin);
        return plugin;
    }

    /**
     * Plugs the the given plugin {@link Object} to this {@link PluginBus}.
     * @param plugin A plugin {@link Object}.
     */
    @SuppressWarnings("unchecked")
    public <T> T plug(final Object plugin) {
        PluginBus.plug(plugin);
        return (T)plugin;
    }

    /**
     * Unplugs the the given plugin {@link Object} to this {@link PluginBus}.
     * @param plugin A plugin {@link Object}.
     */
    @SuppressWarnings("unchecked")
    public void unplug(final Object plugin) {
        PluginBus.unplug(plugin);
    }

    @Override
    //@CallSuper
    public void onPlugged(final PluginBus bus) {
        Log.d(TAG, "onPlugged(...)");
        updateListeners(bus);
    }

    @Override
    //@CallSuper
    public void onUnplugged(final PluginBus bus) {
        Log.d(TAG, "onUnplugged(...)");
        listeners.clear();
    }

    @Override
    //@CallSuper
    public void onPluginPlugged(final Object plugin) {
        if (plugin instanceof PresenterListener) {
            updateListeners(PluginBus.getInstance());
        }
    }

    protected void updateListeners(final PluginBus bus) {
        final List<PresenterListener> plugins = PluginBus.getPlugs(PresenterListener.class, true);
        listeners.clear();
        listeners.addAll(plugins);
    }

    @SuppressWarnings("unchecked")
    @Override
    @CallSuper
    public void onViewCreated(final View view, final Params inState) {
        Log.d(TAG, "onViewCreated(...)");
    }

    @SuppressWarnings("unchecked")
    @Override
    @CallSuper
    public void onViewResume(final View view) {
        resume();
        Log.d(TAG, "onViewResume(View)");
    }

    @SuppressWarnings("unchecked")
    @Override
    @CallSuper
    public void onViewStart(final View view) {
        start();
        Log.d(TAG, "onViewStart(View)");
    }

    @Override
    @CallSuper
    public void onViewPause(final View view) {
        pause();
        Log.d(TAG, "onViewPause(View)");
    }

    @Override
    @CallSuper
    public void onViewStop(final View view) {
        Log.d(TAG, "onViewStop(View)");
    }

    @Override
    @CallSuper
    public void onViewDestroy(final View view) {
        Log.d(TAG, "onViewDestroy(View)");
    }

    @Override
    public DependencyScope getScope() {
        return scope;
    }

    @Override
    public void setScope(final DependencyScope scope) {
        this.scope = scope;
    }

    /**
     * The the {@link Presenter} interface class from the class of the given {@link Presenter}
     * instance.
     * @param presenter A {@link Presenter}.
     * @return A {@link Class}. May return {@code null}.
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Presenter> getInterfaceClass(final Presenter presenter) {
        final Class<? extends Presenter> presenterClass = presenter.getClass();

        for (final Class<?> interfaceClass : presenterClass.getInterfaces()) {
            if (Presenter.class.isAssignableFrom(interfaceClass)) {
                return (Class<? extends Presenter>)interfaceClass;
            }
        }
        return null;
    }
}
