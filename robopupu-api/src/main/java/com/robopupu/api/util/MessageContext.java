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

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

public class MessageContext {

    private final Context context;

    protected final ArrayList<Object> args;

    protected String message;
    protected @StringRes int messageStringResId;

    public MessageContext(final Context context) {
        this.context = context;
        args = new ArrayList<>();
        clear();
    }

    public String getFormattedMessage() {

        final Resources resources = context.getResources();

        if (message != null) {
            return StringToolkit.formatString(message, args);
        } else {
            final int length = args.size();

            if (length > 0) {
                return resources.getString(messageStringResId, args);
            } else {
                return resources.getString(messageStringResId);
            }
        }
    }


    public void setMessage(final String message) {
        this.message = message;
    }

    public void setMessage(@StringRes final int stringResId) {
        messageStringResId = stringResId;
    }

    public void setMessage(@StringRes final int stringResId, final Object... args) {
        messageStringResId = stringResId;
        setMessageArgs(args);
    }


    public void setMessageArgs(final Object... args) {
        this.args.clear();

        for (final Object arg : args) {
            this.args.add(arg);
        }
    }

    public void setMessageArgs(final List<Object> args) {
        this.args.clear();
        this.args.addAll(args);
    }

    public MessageContext addMessageArg(final boolean arg) {
        args.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final byte arg) {
        args.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final char arg) {
        args.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final double arg) {
        args.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final float arg) {
        args.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final int arg) {
        args.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final Object arg) {
        args.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final String arg) {
        args.add(arg);
        return this;
    }

    public void clear() {
        args.clear();
        message = null;
        messageStringResId = 0;
    }

    public boolean hasContent() {
        return (message != null || messageStringResId > 0);
    }
}
