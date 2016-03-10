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

import com.robopupu.app.RobopupuAppScope;

import com.robopupu.api.component.AbstractManager;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.plugin.PluginBus;

import java.util.HashMap;

@Plugin
public class TimerManagerImpl extends AbstractManager implements TimerManager {

    private static final String TAG = TimerManagerImpl.class.getSimpleName();

    private final HashMap<Long, TimerHandle> mTimerHandles;

    @Scope(RobopupuAppScope.class)
    @Provides(TimerManager.class)
    public TimerManagerImpl() {
        mTimerHandles = new HashMap<>();
    }

    @Override
    public TimerHandle createTimer(final Callback callback, final long delay) {
        final TimerHandle handle = new TimerHandle(this, callback, delay);
        mTimerHandles.put(handle.getId(), handle);
        handle.start();
        return handle;
    }

    protected void removeHandle(final TimerHandle handle) {
        mTimerHandles.remove(handle.getId());
    }

    @Override
    public void onUnplugged(final PluginBus bus) {
        super.onUnplugged(bus);

        for (final TimerHandle handle : mTimerHandles.values()) {
            handle.cancel();
        }
        mTimerHandles.clear();
    }
}
