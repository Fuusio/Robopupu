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

import android.support.annotation.NonNull;

import org.fuusio.api.util.Params;

/**
 * {@link AbstractPluginStateComponent} provides an abstract base class for implementing
 * {@link PluginStateComponent}s.
 */
public class AbstractPluginStateComponent extends AbstractPluginComponent
        implements PluginStateComponent {

    private final PluginState mState;

    protected Params mParams;

    protected AbstractPluginStateComponent() {
        mState = new PluginState();
    }

    /**
     * Initialize this {@link PluginComponent} with the given parameters.
     * @param params A {@link Params} containing the parameters.
     */
    private void init(final Params params) {
        mState.onCreate();
        onCreate(params);
    }

    @Override
    public void pause() {
        mState.onPause();
        onPause();
    }

    @Override
    public void resume() {
        mState.onResume();
        onResume();
    }

    @Override
    public void restart() {
        mState.onRestart();
        onRestart();
    }

    @Override
    public final void start() {
        start(new Params());
    }

    @Override
    public void start(final Params params) {
        if (mState.isDormant()) {
            init(params);
        }
        mState.onStart();
        onStart();
    }

    @Override
    public void stop() {
        mState.onStop();
        PluginBus.unplug(this);
        onStop();
    }

    @Override
    public void destroy() {
        mState.onDestroy();
        onDestroy();
    }

    @NonNull
    @Override
    public PluginState getState() {
        return mState;
    }

    @SuppressWarnings("unused")
    protected void onCreate(final Params params) {
        // By default do nothing
    }

    protected void onStart() {
        // By default do nothing
    }

    protected void onRestart() {
        // By default do nothing
    }

    protected void onResume() {
        // By default do nothing
    }

    protected void onPause() {
        // By default do nothing
    }

    protected void onStop() {
        // By default do nothing
    }

    protected void onDestroy() {
        // By default do nothing
    }

    @Override
    public boolean isPaused() {
        return mState.isPaused();
    }

    @Override
    public boolean isRestarted() {
        return mState.isRestarted();
    }

    @Override
    public boolean isResumed() {
        return mState.isResumed();
    }

    @Override
    public boolean isStarted() {
        return mState.isStarted();
    }

    @Override
    public boolean isStopped() {
        return mState.isStopped();
    }

    @Override
    public boolean isDestroyed() {
        return mState.isDestroyed();
    }

}
