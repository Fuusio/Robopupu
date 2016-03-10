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

/**
 * {@link Message} implements a generic object for passing a message with an identifier and optional
 * arguments.
 */
public class Message {

    private final int mId;
    private final Object[] mArgs;

    private Message(final int id, final Object[] args) {
        mId = id;
        mArgs = args;
    }

    public static Message create(final int id, final Object... args) {
        return new Message(id, args);
    }

    public final int getId() {
        return mId;
    }

    public final Object[] getArgs() {
        return mArgs;
    }
}
