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

    private final TimerManager.Callback mCallback;
    private final long mDelay;
    private final Handler mHandler;
    private final long mId;
    private final Runnable mRunnable;
    private final TimerManagerImpl mTimerManager;

    protected TimerHandle(final TimerManagerImpl timerManager, final TimerManager.Callback callback, final long delay) {
        mTimerManager = timerManager;
        mCallback = callback;
        mDelay = delay;
        mHandler = new Handler(Looper.getMainLooper());
        mId = System.currentTimeMillis();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mCallback.timeout(TimerHandle.this);
                mTimerManager.removeHandle(TimerHandle.this);
            }
        };
    }

    protected void start() {
        mHandler.postDelayed(mRunnable, mDelay);
    }

    public long getId() {
        return mId;
    }

    public void cancel() {
        mHandler.removeCallbacks(mRunnable);
        mTimerManager.removeHandle(this);
    }
}
