/*
 * Copyright (C) 2016 Marko Salmela, http://robopupu.com
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
package com.robopupu.component;

import com.robopupu.api.component.AbstractManager;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.api.util.Utils;
import com.robopupu.app.RobopupuAppScope;

import java.util.HashMap;

@Plugin
public class TimerManagerImpl extends AbstractManager
        implements TimerManager, ExitObserver {

    private static final String TAG = Utils.tag(TimerManagerImpl.class);

    private final HashMap<Long, TimerHandle> timerHandles;

    @Scope(RobopupuAppScope.class)
    @Provides(TimerManager.class)
    public TimerManagerImpl() {
        timerHandles = new HashMap<>();
    }

    @Override
    public TimerHandle createTimer(final Callback callback, final long delay) {
        final TimerHandle handle = new TimerHandle(this, callback, delay);
        timerHandles.put(handle.getId(), handle);
        handle.start();
        return handle;
    }

    protected void removeHandle(final TimerHandle handle) {
        timerHandles.remove(handle.getId());
    }

    @Override
    public void onUnplugged(final PluginBus bus) {
        super.onUnplugged(bus);
        cancelTimers();
    }

    @Override
    public void onAppExit() {
        cancelTimers();
    }

    private void cancelTimers() {
        for (final TimerHandle handle : timerHandles.values()) {
            handle.cancel();
        }
        timerHandles.clear();
    }
}
