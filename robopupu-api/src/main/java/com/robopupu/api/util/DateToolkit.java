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

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToolkit {

    public final static String DEFAULT = "MM/dd/yyyy hh:mm:ss a Z";
    public final static String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ";
    public final static String ISO8601_NOMS = "yyyy-MM-dd'T'HH:mm:ssZ";
    public final static String RFC822 = "EEE, dd MMM yyyy HH:mm:ss Z";
    public final static String SIMPLE = "MM/dd/yyyy hh:mm:ss a";

    @SuppressLint("SimpleDateFormat")
    public static String format(final String format, final Date date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String format(final Date date) {
        return format(DEFAULT, date);
    }

    public static String formatISO8601(final Date date) {
        return format(ISO8601, date);
    }

    public static String formatISO8601NoMilliseconds(final Date date) {
        return format(ISO8601_NOMS, date);
    }

    public static String formatRFC822(final Date date) {
        return format(RFC822, date);
    }

    public static String formatSimple(final Date date) {
        return format(SIMPLE, date);
    }

    public static Date parse(final String date) throws ParseException {
        return parse(DEFAULT, date);
    }

    @SuppressLint("SimpleDateFormat")
    public static Date parse(final String format, final String date) throws ParseException {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    public static Date parseISO8601(final String date) throws ParseException {
        return parse(ISO8601, date);
    }

    public static Date parseISO8601NoMilliseconds(final String date) throws ParseException {
        return parse(ISO8601_NOMS, date);
    }

    public static Date parseRFC822(final String date) throws ParseException {
        return parse(RFC822, date);
    }

    public static Date parseSimple(final String date) throws ParseException {
        return parse(SIMPLE, date);
    }
}
