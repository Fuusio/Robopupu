package com.robopupu.api.graph.nodes;

import android.os.Handler;
import android.os.Looper;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.OutputNode;

import java.util.HashMap;

/**
 * {@link TimerNode} TODO
 * @param <IN>
 */
public class TimerNode<IN> extends AbstractNode<IN, IN> {

    private final HashMap<Long, TimerHandle> mTimerHandles;
    private final long mDelay;
    private final long mInterval;
    private final int mRepeatCount;

    public TimerNode(final long delay) {
        this(delay, 0L, 0);
    }

    public TimerNode(final long delay, final long interval, final int repeatCount) {
        mTimerHandles = new HashMap<>();
        mDelay = delay;
        mInterval = interval;
        mRepeatCount = repeatCount;
    }

    @Override
    public void onInput(final OutputNode<IN> outputNode, final IN input) {
        start(input);
    }

    /**
     * Stars a new timer.
     */
    public void start(final IN input) {
        final TimerHandle handle = new TimerHandle(this, input, mDelay, mInterval, mRepeatCount);
        handle.start();
    }

    protected void timeout(final TimerHandle handle, final IN input) {
        out(input);

        if (handle.isFinished()) {
            remove(handle);
        }
    }

    protected void remove(final TimerHandle handle) {
        mTimerHandles.remove(handle.getId());
    }

    private class TimerHandle {

        private final long mDelay;
        private final Handler mHandler;
        private final long mId;
        private final IN mInput;
        private final long mInterval;
        private final int mRepeatCount;
        private final Runnable mRunnable;
        private final TimerNode<IN> mTimerNode;

        private boolean mCancelled;
        private int mTimeoutCounter;

        protected TimerHandle(final TimerNode<IN> timerNode, final IN input, final long delay, final long interval, final int repeatCount) {
            mTimerNode = timerNode;
            mInput = input;
            mDelay = delay;
            mHandler = new Handler(Looper.getMainLooper());
            mId = System.currentTimeMillis();
            mInterval = interval;
            mRepeatCount = repeatCount;
            mRunnable = this::timeout;
            mCancelled = false;
            mTimeoutCounter = 0;
        }

        protected void start() {
            mHandler.postDelayed(mRunnable, mDelay);
        }

        public long getId() {
            return mId;
        }

        public void cancel() {
            mCancelled = true;
            mHandler.removeCallbacks(mRunnable);
            mTimerNode.remove(this);
        }

        private void timeout() {
            mTimerNode.timeout(this, mInput);
            mTimeoutCounter++;

            if (mTimeoutCounter < mRepeatCount) {
                mHandler.postDelayed(mRunnable, mInterval);
            }
        }

        public boolean isFinished() {
            return mCancelled || mTimeoutCounter >= mRepeatCount;
        }
    }
}
