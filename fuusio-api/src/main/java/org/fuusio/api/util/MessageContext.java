/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://fuusio.org
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
package org.fuusio.api.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

public class MessageContext {

    private final Context mContext;

    protected final ArrayList<Object> mArgs;

    protected String mMessage;
    protected int mMessageId;

    public MessageContext(final Context context) {
        mContext = context;
        mArgs = new ArrayList<>();
        clear();
    }

    public String getFormattedMessage() {

        final Resources resources = mContext.getResources();

        if (mMessage != null) {
            return StringToolkit.formatString(mMessage, mArgs);
        } else {
            final int length = mArgs.size();

            if (length > 0) {
                return resources.getString(mMessageId, mArgs);
            } else {
                return resources.getString(mMessageId);
            }
        }
    }


    public void setMessage(final String message) {
        mMessage = message;
    }

    public void setMessage(@StringRes final int stringResId) {
        mMessageId = stringResId;
    }

    public void setMessage(@StringRes final int stringResId, final Object... args) {
        mMessageId = stringResId;
        setMessageArgs(args);
    }


    public void setMessageArgs(final Object... args) {
        mArgs.clear();

        for (final Object arg : args) {
            mArgs.add(arg);
        }
    }

    public void setMessageArgs(final List<Object> args) {
        mArgs.clear();
        mArgs.addAll(args);
    }

    public MessageContext addMessageArg(final boolean arg) {
        mArgs.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final byte arg) {
        mArgs.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final char arg) {
        mArgs.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final double arg) {
        mArgs.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final float arg) {
        mArgs.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final int arg) {
        mArgs.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final Object arg) {
        mArgs.add(arg);
        return this;
    }

    public MessageContext addMessageArg(final String arg) {
        mArgs.add(arg);
        return this;
    }

    public void clear() {
        mArgs.clear();
        mMessage = null;
        mMessageId = 0;
    }

    public boolean hasContent() {
        return (mMessage != null || mMessageId > 0);
    }
}
