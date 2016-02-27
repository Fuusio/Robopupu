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
