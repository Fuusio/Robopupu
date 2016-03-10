/*
 * (C) Copyright 2009-2013 Marko Salmela (http://robopupu.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robopupu.api.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class LogFile {

    public final static String PREFIX_LOG_FILE = "Log_";

    protected final static String TYPE_DEBUG = "DEBUG - ";
    protected final static String TYPE_INFO = "INFO - ";
    protected final static String TYPE_WARNING = "WARNING - ";
    protected final static String TYPE_ERROR = "ERROR - ";
    protected final static String TYPE_WTF = "WTF - ";

    protected final static String DATE_FORMAT = "yyyyMMdd_HHmmss";
    protected final static String TIME_FORMAT = "HHmmss";

    protected static LogFile sInstance = null;

    protected SimpleDateFormat mTimeFormat;
    protected Date mCreatedDate;
    protected int mErrorCount;
    protected File mFile;
    protected FileWriter mWriter;
    protected boolean mUseInternalDirectory;

    private LogFile() {
        sInstance = this;
        mUseInternalDirectory = true;
    }

    private LogFile(final boolean useInternalDirectory) {
        this();
        mUseInternalDirectory = useInternalDirectory;
    }

    private LogFile(final Date createdDate, final boolean useInternalDirectory) {
        this(useInternalDirectory);

        mCreatedDate = createdDate;
        mErrorCount = 0;
        mTimeFormat = new SimpleDateFormat(TIME_FORMAT);
    }

    public final Date getCreatedDate() {
        return mCreatedDate;
    }

    public static LogFile getInstance() {
        return sInstance;
    }

    public final File getFile() {
        return mFile;
    }

    public final boolean isErrorDetected() {
        return (mErrorCount > 0);
    }

    @SuppressLint("SimpleDateFormat")
    @SuppressWarnings("resource")
    public static LogFile start(final Context context, final String appName) {
        return start(context, appName, true);
    }

    @SuppressLint("SimpleDateFormat")
    @SuppressWarnings("resource")
    public static LogFile start(final Context context, final String appName, final boolean useInternalDirectory) {

        final Date createdDate = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        final StringBuilder fileName = new StringBuilder(PREFIX_LOG_FILE);
        fileName.append(appName);
        fileName.append("_");
        fileName.append(dateFormat.format(createdDate));
        fileName.append(".txt");


        final File directory = useInternalDirectory ? context.getFilesDir() : Environment.getExternalStorageDirectory();
        final File outputFile = new File(directory, fileName.toString());
        //FileWriter writer = null;

        if (!outputFile.exists()) {
            try {
                if (outputFile.createNewFile());
            } catch (IOException ignore) {
                // Do nothing
            }
        }

        final LogFile logFile = new LogFile(createdDate, useInternalDirectory);
        logFile.mFile = outputFile;
        logFile.mWriter = null;
        L.setLogFile(logFile);
        return logFile;
    }

    public static boolean stop() {
        if (sInstance != null) {

            L.setLogFile(null);

            if (sInstance.mWriter != null) {
                try {
                    sInstance.mWriter.flush();
                    sInstance.mWriter.close();
                } catch (IOException ignore) {
                    // Do nothing
                }
            }

            sInstance.mFile = null;
            sInstance.mWriter = null;
            sInstance = null;
        }
        return false;
    }

    public void info(final String tag, final String message) {
        write(TYPE_INFO, tag, message);
    }

    public void debug(final String tag, final String message) {
        write(TYPE_DEBUG, tag, message);
    }

    public void error(final String tag, final String message) {
        mErrorCount++;
        write(TYPE_ERROR, tag, message);
    }

    public void warning(final String tag, final String message) {
        write(TYPE_WARNING, tag, message);
    }

    public void wtf(final String tag, final String message) {
        mErrorCount++;
        write(TYPE_WTF, tag, message);
    }

    protected synchronized void write(final String type, final String tag, final String message) {
        if (mFile != null) {
            try {
                mWriter = new FileWriter(mFile, true);

                mWriter.append(mTimeFormat.format(new Date()));
                mWriter.append(" ");
                mWriter.append(type);
                mWriter.append(tag);
                mWriter.append(" : ");
                mWriter.append(message);
                mWriter.append("\n");
                mWriter.flush();
                mWriter.close();
                mWriter = null;
            } catch (IOException ignore) {
                // Do nothing
            }
        }
    }

    public final int getErrorCount() {
        return mErrorCount;
    }
}
