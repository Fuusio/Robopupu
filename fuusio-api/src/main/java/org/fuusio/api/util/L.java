package org.fuusio.api.util;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class L extends Object {

    private static LogFile sLogFile = null;

    public static int i(final Object object, final String method,
                        final String message) {
        final String tag = createTag(object, method);

        if (sLogFile != null) {
            sLogFile.info(tag, message);
        }
        return Log.i(tag, message);
    }

    public static int d(final Object object, final String method,
                        final String message) {
        final String tag = createTag(object, method);

        if (sLogFile != null) {
            sLogFile.debug(tag, message);
        }
        return Log.d(tag, message);
    }

    public static int e(final Object object, final String method,
                        final String message) {
        final String tag = createTag(object, method);

        if (sLogFile != null) {
            sLogFile.error(tag, message);
        }
        return Log.e(tag, message);
    }

    public static void e(final Object object, final String method, final Exception exception) {
        L.e(object, method, exception.getMessage());

        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        printWriter.flush();
        L.e(object, method, printWriter.toString());
        printWriter.close();
    }

    public static int w(final Object object, final String method,
                        final String message) {
        final String tag = createTag(object, method);

        if (sLogFile != null) {
            sLogFile.warning(tag, message);
        }
        return Log.w(createTag(object, method), message);
    }

    public static int wtf(final Object object, final String method,
                          final String message) {
        final String tag = createTag(object, method);

        if (sLogFile != null) {
            sLogFile.wtf(tag, message);
        }
        return Log.wtf(createTag(object, method), message);
    }

    public static String createTag(final Object object, final String method) {
        final StringBuilder tag = new StringBuilder(object.getClass()
                .getSimpleName());
        tag.append('.');
        tag.append(method);
        return tag.toString();
    }

    public static void wtf(final Object object, final String method,
                           final Exception exception) {
        L.wtf(object, method, exception.getMessage());
        exception.printStackTrace();
    }

    public static void setLogFile(final LogFile logFile) {
        sLogFile = logFile;
    }

    public static void writeMessage(final String message) {
        if (sLogFile != null) {
            sLogFile.write("", "", message);
        }
    }
}
