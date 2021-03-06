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

import com.robopupu.api.component.Manager;
import com.robopupu.api.plugin.PlugInterface;

@PlugInterface
public interface TimerManager extends Manager {

    /**
     * Creates and starts a timer that expires after the specified delay.
     * @param callback A {@link Callback} to be invoked when a timer expires.
     * @param delay The delay in milliseconds before the timer expires.
     * @return A {@link TimerHandle} that can be used to cancel the timer.
     */
    TimerHandle createTimer(Callback callback, long delay);

    interface Callback {
        void timeout(TimerHandle handle);
    }
}
