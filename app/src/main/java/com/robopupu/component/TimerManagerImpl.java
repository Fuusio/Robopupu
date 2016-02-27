/*
 * Copyright (C) 2016 Marko Salmela, http://fuusio.org
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

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.robopupu.R;
import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.app.RobopupuAppScope;
import com.robopupu.app.RobopupuApplication;

import org.fuusio.api.component.AbstractManager;
import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.plugin.PluginBus;

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
