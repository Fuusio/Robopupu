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
import org.fuusio.api.plugin.PluginComponent;
import org.fuusio.api.util.AbstractListenable;
import org.fuusio.api.util.LifecycleState;

/**
 * {@link AbstractPresenter} extends {@link AbstractListenable} to provide an abstract base class
 * for concrete implementations of {@link Presenter}s.
 */
public abstract class AbstractPresenter<T_View extends View> extends AbstractPluginComponent
        implements Presenter, Scopeable {

    protected LifecycleState mState;

    private DependencyScope mScope; // TODO

    protected AbstractPresenter() {
        mState = LifecycleState.CREATED;
    }

    /**
     * Gets the {@link View} attached to this {@link Presenter}.
     *
     * @return A {@link View}.
     */
    public abstract T_View getView();

    /**
     * Gets the {@link PresenterListener}.
     * @return A {@link PresenterListener}. May be {@link null}.
     */
    protected PresenterListener getListener() {
        return null; // TODO
    }

    /**
     * Gets the {@link ViewState} of the attached {@link View}.
     * @return A {@link ViewState}.
     */
    @NonNull
    protected ViewState getViewState() {
        return getView().getState();
    }

    /**
     * Invoked to pause this {@link Presenter}.
     */
    private void pause() {
        mState = LifecycleState.PAUSED;

        final PresenterListener listener = getListener();
        if (listener != null) {
            listener.onPresenterPaused(this);
        }
    }

    /**
     * Invoked to resume this {@link Presenter}.
     */
    private void resume() {
        mState = LifecycleState.RESUMED;

        final PresenterListener listener = getListener();
        if (listener != null) {
            listener.onPresenterResumed(this);
        }
    }

    /**
     * Invoked to start this {@link Presenter}.
     */
     private void start() {
        mState = LifecycleState.STARTED;

         final PresenterListener listener = getListener();
         if (listener != null) {
            listener.onPresenterStarted(this);
        }
    }

    /**
     * Invoked to stop this {@link Presenter}.
     */
    private void stop() {
        if (!mState.isStopped() && !mState.isDestroyed()) {
            mState = LifecycleState.STOPPED;

            final PresenterListener listener = getListener();
            if (listener != null) {
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

            final PresenterListener listener = getListener();
            if (listener != null) {
                listener.onPresenterDestroyed(this);
            }
        }
    }

    @Override
    public void finish() {
        stop();

        final PresenterListener listener = getListener();
        if (listener != null) {
            listener.onPresenterFinished(this);
        }
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Class<?> dependencyType) {
        return (T) D.get(mScope, dependencyType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Class<?> dependencyType, final Object dependant) {
        return (T)D.get(mScope, dependencyType, dependant);
    }
}
