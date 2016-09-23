package com.robopupu.api.util;

/**
 * {@link DefaultLogDelagate} provides a default implemenation of {@link LogDelegate} that utilises
 * {@link android.util.Log} for implementation.
 */

public class DefaultLogDelagate implements LogDelegate {
    
    @Override
    public int v(final String tag, final String message) {
        return android.util.Log.v(tag, message);
    }

    @Override
    public int v(final String tag, final String message, final Throwable throwable) {
        return android.util.Log.v(tag, message, throwable);
    }

    @Override
    public int d(final String tag, final String message) {
        return android.util.Log.d(tag, message);
    }

    @Override
    public int d(final String tag, final String message, final Throwable throwable) {
        return android.util.Log.d(tag, message, throwable);
    }

    @Override
    public int i(final String tag, final String message) {
        return android.util.Log.i(tag, message);
    }

    @Override
    public int i(final String tag, final String message, final Throwable throwable) {
        return android.util.Log.i(tag, message, throwable);
    }

    @Override
    public int w(final String tag, final String message) {
        return android.util.Log.w(tag, message);
    }

    @Override
    public int w(final String tag, final String message, final Throwable throwable) {
        return android.util.Log.w(tag, message, throwable);
    }

    @Override
    public boolean isLoggable(final String tag, final int level) {
        return android.util.Log.isLoggable(tag, level);
    }

    @Override
    public int w(final String tag, final Throwable throwable) {
        return android.util.Log.w(tag, throwable);
    }

    @Override
    public int e(final String tag, final String message) {
        return android.util.Log.e(tag, message);
    }

    @Override
    public int e(final String tag, final String message, final Throwable throwable) {
        return android.util.Log.e(tag, message, throwable);
    }

    @Override
    public int wtf(final String tag, final String message) {
        return android.util.Log.wtf(tag, message);
    }

    @Override
    public int wtf(final String tag, final Throwable throwable) {
        return android.util.Log.wtf(tag, throwable);
    }

    @Override
    public int wtf(final String tag, final String message, final Throwable throwable) {
        return android.util.Log.wtf(tag, message, throwable);
    }

    @Override
    public String getStackTraceString(final Throwable throwable) {
        return android.util.Log.getStackTraceString(throwable);
    }

    @Override
    public int println(final int priority, final String tag, final String message) {
        return android.util.Log.println(priority, tag, message);
    }
}
