/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://fuusio.org
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
package org.fuusio.api.mvp;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.Scopeable;
import org.fuusio.api.plugin.AbstractPluginComponent;
import org.fuusio.api.plugin.PlugInvoker;
import org.fuusio.api.plugin.PluginBus;
import org.fuusio.api.util.AbstractListenable;
import org.fuusio.api.util.LifecycleState;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AbstractPresenter} extends {@link AbstractListenable} to provide an abstract base class
 * for concrete implementations of {@link Presenter}s.
 */
public abstract class AbstractPresenter<T_View extends View> extends AbstractPluginComponent
        implements Presenter, Scopeable {

    protected final List<PresenterListener> mListneners;
    protected LifecycleState mState;

    private DependencyScope mScope; // TODO

    protected AbstractPresenter() {
        mListneners = new ArrayList<>();
        mState = LifecycleState.CREATED;
    }

    /**
     * Gets the {@link View} attached to this {@link Presenter}.
     *
     * @return A {@link View}.
     */
    protected T_View getAttachedView() {
        final T_View viewPlug = getViewPlug();

        if (viewPlug instanceof PlugInvoker) {
            return ((PlugInvoker<T_View>)viewPlug).get(0);
        } else {
            return viewPlug;
        }
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
    @NonNull
    protected ViewState getViewState() {
        return getAttachedView().getState();
    }

    /**
     * Invoked to pause this {@link Presenter}.
     */
    private void pause() {
        mState = LifecycleState.PAUSED;

        for (final PresenterListener listener : mListneners) {
            listener.onPresenterPaused(this);
        }
    }

    /**
     * Invoked to resume this {@link Presenter}.
     */
    private void resume() {
        mState = LifecycleState.RESUMED;

        for (final PresenterListener listener : mListneners) {
            listener.onPresenterResumed(this);
        }
    }

    /**
     * Invoked to start this {@link Presenter}.
     */
     private void start() {
        mState = LifecycleState.STARTED;

         for (final PresenterListener listener : mListneners) {
            listener.onPresenterStarted(this);
        }
    }

    /**
     * Invoked to stop this {@link Presenter}.
     */
    private void stop() {
        if (!mState.isStopped() && !mState.isDestroyed()) {
            mState = LifecycleState.STOPPED;

            for (final PresenterListener listener : mListneners) {
                listener.onPresenterStopped(this);
            }
        }
    }

    /**
     * Invoked to destroy this {@link Presenter}.
     */
    private void destroy() {

        if (!mState.isDestroyed()) {

            mState = LifecycleState.DESTROYED;

            for (final PresenterListener listener : mListneners) {
                listener.onPresenterDestroyed(this);
            }
        }
    }

    @Override
    public void finish() {
        stop();

        for (final PresenterListener listener : mListneners) {
            listener.onPresenterFinished(this);
        }
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
    public <T> T  plug(final Object plugin) {
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
    public void onPlugged(final PluginBus bus) {
        updateListeners(bus);
    }

    @Override
    public void onUnplugged(final PluginBus bus) {
        mListneners.clear();
    }

    @Override
    public void onPluginPlugged(final Object plugin) {
        if (plugin instanceof PresenterListener) {
            updateListeners(PluginBus.getInstance());
        }
    }

    protected void updateListeners(final PluginBus bus) {
        final List<PresenterListener> plugins = bus.getPlugs(PresenterListener.class, true);
        mListneners.clear();
        mListneners.addAll(plugins);
    }

    @SuppressWarnings("unchecked")
    @Override
    @CallSuper
    public void onViewCreated(final View view, final Bundle inState) {
    }

    @SuppressWarnings("unchecked")
    @Override
    @CallSuper
    public void onViewResume(final View view) {
        resume();
    }

    @SuppressWarnings("unchecked")
    @Override
    @CallSuper
    public void onViewStart(final View view) {
        start();
    }

    @Override
    @CallSuper
    public void onViewPause(final View view) {
        pause();
    }


    @Override
    @CallSuper
    public void onViewStop(final View view) {
        stop();
    }

    @Override
    @CallSuper
    public void onViewDestroy(final View view) {
        destroy();
    }

    @Override
    public DependencyScope getScope() {
        return mScope;
    }

    @Override
    public void setScope(final DependencyScope scope) {
        mScope = scope;
    }
}
