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
package com.robopupu.api.util;

import java.text.MessageFormat;

/**
 * {@code ExceptionToolkit} provides a set of convenience methods and utilities for throwing,
 * handling, and using {@link Exception}s.
 */
public class ExceptionToolkit {

    /**
     * Asserts that the given parameter value is not {@code null}. If {@code null}, throws an
     * {@link IllegalArgumentException}.
     *
     * @param parameterValue The parameter value to be tested.
     * @param parameterName  The name of the parameter.
     */
    public static void assertArgumentNotNull(final Object parameterValue,
                                             final String parameterName) {
        if (parameterValue == null) {
            final String message = "Parameter '{0}' may not be null.";
            throw new IllegalArgumentException(StringToolkit.formatString(message,
                    parameterName));
        }
    }

    /**
     * Formats the given message {@link String} with given two arguments and by using the
     * {@link MessageFormat} class.
     *
     * @param message The message to be displayed as {@link String}.
     * @param args    The optional arguments for {@link MessageFormat} class.
     * @return A {@link String} containing the formatted message.
     */
    public static String formatMessageString(final String message, final Object... args) {
        return StringToolkit.formatString(message, args);
    }

    /**
     * Creates and throws an {@link IllegalArgumentException} with the given message.
     *
     * @param message The message to be displayed as {@link String}.
     */
    public static void throwIllegalArgumentException(final String message) {
        throw new IllegalArgumentException(message);
    }

    /**
     * Creates and throws an {@link IllegalStateException} with the given message.
     *
     * @param message The message to be displayed as {@link String}.
     */
    public static void throwIllegalStateException(final String message) {
        throw new IllegalStateException(message);
    }

    /**
     * Creates and throws an {@link IllegalArgumentException} with the given message and message
     * formatting arguments.
     *
     * @param message The message to be displayed as {@link String}.
     * @param args    The optional message formatting arguments.
     */
    public static void throwIllegalArgumentException(final String message, final Object... args) {
        final String errorMessage = StringToolkit.formatString(message, args);
        throw new IllegalArgumentException(errorMessage);
    }

    /**
     * Creates and throws an {@link IllegalArgumentException} with the given message and message
     * formatting arguments.
     *
     * @param parameterName The name of the parameter as {@link String}.
     */
    public static void throwNullParameterException(final String parameterName) {
        final String message = "Parameter '{0}' may not be null.";
        throw new IllegalArgumentException(StringToolkit.formatString(message,
                parameterName));
    }
}
