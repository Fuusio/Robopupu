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

import android.os.Handler;
import android.os.Looper;

/**
 * {@link TimerHandle} is an object that can be used to cancel a timer started by {@link TimerManager}.
 */
public class TimerHandle {

    private final TimerManager.Callback callback;
    private final long delay;
    private final Handler handler;
    private final long id;
    private final Runnable runnable;
    private final TimerManagerImpl timerManager;

    protected TimerHandle(final TimerManagerImpl timerManager, final TimerManager.Callback callback, final long delay) {
        this.timerManager = timerManager;
        this.callback = callback;
        this.delay = delay;
        handler = new Handler(Looper.getMainLooper());
        id = System.currentTimeMillis();
        runnable = new Runnable() {
            @Override
            public void run() {
                TimerHandle.this.callback.timeout(TimerHandle.this);
                TimerHandle.this.timerManager.removeHandle(TimerHandle.this);
            }
        };
    }

    protected void start() {
        handler.postDelayed(runnable, delay);
    }

    public long getId() {
        return id;
    }

    public void cancel() {
        handler.removeCallbacks(runnable);
        timerManager.removeHandle(this);
    }
}
