/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.app;

import android.util.Log;

import java.lang.Thread.UncaughtExceptionHandler;

public abstract class AppExceptionHandler<T_ApplicationError extends AppError> implements UncaughtExceptionHandler {

    private static AppExceptionHandler instance = null;

    private final Thread.UncaughtExceptionHandler defaultHandler;

    protected AppExceptionHandler(final Thread.UncaughtExceptionHandler defaultHandler) {
        instance = this;
        this.defaultHandler = defaultHandler;
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable throwable) {
        createLogEntryFor(thread, getUnknownError(), throwable);
    }

    public static void createLogEntryFor(final Object object, final Throwable throwable) {
        createLogEntryFor(object, getUnknownError(), throwable);
    }

    public static void createLogEntryFor(final Object object, final String message) {
        createLogEntryFor(object, getUnknownError(), message);
    }

    public static void createLogEntryFor(final Object object, final AppError error, final String message) {
        if (message != null) {
            final String tag = (object != null) ? object.toString() : AppExceptionHandler.class.getSimpleName();
            Log.e(tag, "ERROR  : " + message);
        }
    }

    public static void createLogEntryFor(final Object object, final AppError error, final Throwable throwable) {
        if (throwable != null) {
            final String stackTrace = Log.getStackTraceString(throwable);
            final String tag = (object != null) ? object.toString() : AppExceptionHandler.class.getSimpleName();
            Log.e(tag, "ERROR : UncaughtException: " + throwable.getMessage());
            Log.e(tag, "ERROR : Stack Trace : " + stackTrace);
        }
    }

    protected static AppError getUnknownError() {
        return instance.getAppSpecificUnknownError();
    }

    protected abstract T_ApplicationError getAppSpecificUnknownError();

}
