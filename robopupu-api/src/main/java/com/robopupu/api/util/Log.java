package com.robopupu.api.util;

/**
 * {@link Log} is an object that can be used for logging instead of Android's {@code android.util.Log}
 */

public class Log {

    private static LogDelegate delegate = null;
    private static boolean enabled = true;


    public static void setDelegate(final LogDelegate delegate) {
        Log.delegate = delegate;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(final boolean enabled) {
        Log.enabled = enabled;
    }

    /**
     * Send a {@code #VERBOSE} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static int v(final String tag, final String message) {
        if (delegate != null && enabled) {
            return delegate.v(tag, message);
        }
        return -1;
    }

    /**
     * Send a {@code #VERBOSE} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log
     */
    public static int v(final String tag, final String message, final Throwable throwable) {
        if (delegate != null && enabled) {
            return delegate.v(tag, message, throwable);
        }
        return -1;
    }

    /**
     * Send a {@code #DEBUG} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static int d(final String tag, final String message) {
        if (delegate != null && enabled) {
            return delegate.d(tag, message);
        }
        return -1;
    }

    /**
     * Send a {@code #DEBUG} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log
     */
    public static int d(final String tag, final String message, final Throwable throwable) {
        if (delegate != null && enabled) {
            return delegate.d(tag, message, throwable);
        }
        return -1;
    }

    /**
     * Send an {@code #INFO} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static int i(final String tag, final String message) {
        if (delegate != null && enabled) {
            return delegate.i(tag, message);
        }
        return -1;
    }

    /**
     * Send a {@code #INFO} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log
     */
    public static int i(final String tag, final String message, final Throwable throwable) {
        if (delegate != null && enabled) {
            return delegate.i(tag, message, throwable);
        }
        return -1;
    }

    /**
     * Send a {@code #WARN} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static int w(final String tag, final String message) {
        if (delegate != null && enabled) {
            return delegate.w(tag, message);
        }
        return -1;
    }

    /**
     * Send a {@code #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log
     */
    public static int w(final String tag, final String message, final Throwable throwable) {
        if (delegate != null && enabled) {
            return delegate.w(tag, message, throwable);
        }
        return -1;
    }

    /**
     * Checks to see whether or not a log for the specified tag is loggable at the specified level.
     *
     *  The default level of any tag is set to INFO. This means that any level above and including
     *  INFO will be logged. Before you make any calls to a logging method you should check to see
     *  if your tag should be logged. You can change the default level by setting a system property:
     *      'setprop log.tag.&lt;YOUR_LOG_TAG> &lt;LEVEL>'
     *  Where level is either VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT, or SUPPRESS. SUPPRESS will
     *  turn off all logging for your tag. You can also create a local.prop file that with the
     *  following in it:
     *      'log.tag.&lt;YOUR_LOG_TAG>=&lt;LEVEL>'
     *  and place that in /data/local.prop.
     *
     * @param tag The tag to check.
     * @param level The level to check.
     * @return Whether or not that this is allowed to be logged.
     * @throws IllegalArgumentException is thrown if the tag.length() > 23.
     */
    public static boolean isLoggable(final String tag, final int level) {
        if (delegate != null) {
            return delegate.isLoggable(tag, level);
        }
        return false;
    }

    /*
     * Send a {@code #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param throwable An exception to log
     */
    public static int w(final String tag, final Throwable throwable) {
        if (delegate != null && enabled) {
            return delegate.w(tag, throwable);
        }
        return -1;
    }

    /**
     * Send an {@code #ERROR} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static int e(final String tag, final String message) {
        if (delegate != null && enabled) {
            return delegate.e(tag, message);
        }
        return -1;
    }
    
    /**
     * Send a {@code #ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log
     */
    public static int e(final String tag, final String message, final Throwable throwable) {
        if (delegate != null && enabled) {
            return delegate.e(tag, message, throwable);
        }
        return -1;
    }
    
    /**
     * What a Terrible Failure: Report a condition that should never happen.
     * The error will always be logged at level ASSERT with the call stack.
     * 
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    public static int wtf(final String tag, final String message) {
        if (delegate != null && enabled) {
            return delegate.wtf(tag, message);
        }
        return -1;
    }
    
    /**
     * What a Terrible Failure: Report an exception that should never happen.
     * Similar to {@link #wtf(String, String)}, with an exception to log.
     * @param tag Used to identify the source of a log message.
     * @param throwable An exception to log.
     */
    public static int wtf(final String tag, final Throwable throwable) {
        if (delegate != null && enabled) {
            return delegate.wtf(tag, throwable);
        }
        return -1;
    }
    
    /**
     * What a Terrible Failure: Report an exception that should never happen.
     * Similar to {@link #wtf(String, Throwable)}, with a message as well.
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     * @param throwable A {@link Throwable} to log.  May be {@code null}.
     */
    public static int wtf(final String tag, final String message, final Throwable throwable) {
        if (delegate != null && enabled) {
            return delegate.wtf(tag, message, throwable);
        }
        return -1;
  
    }

    /**
     * Handy function to get a loggable stack trace from a {@link Throwable}
     * @param throwable An exception to log
     */
    public static String getStackTraceString(final Throwable throwable) {
        if (delegate != null) {
            return delegate.getStackTraceString(throwable);
        }
        return null;
    }
    
    /**
     * Low-level logging call.
     * @param priority The priority/type of this log message
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     * @return The number of bytes written.
     */
    public static int println(final int priority, final String tag, final String message) {
        if (delegate != null && enabled) {
            return delegate.println(priority, tag, message);
        }
        return -1;
    }
}
