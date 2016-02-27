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
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;

import com.robopupu.app.RobopupuApplication;

import org.fuusio.api.component.Manager;
import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface TimerManager extends Manager {

    /**
     * Creates and starts a timer that expires after the specified delay.
     * @param delay delay in milliseconds before the timer expires.
     * @return A {@link TimerHandle} that can be used to cancel the timer.
     */
    TimerHandle createTimer(Callback callback, long delay);

    interface Callback {
        void timeout(TimerHandle handle);
    }
}
