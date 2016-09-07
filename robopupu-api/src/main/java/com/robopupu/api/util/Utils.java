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
 * {@link Utils} is a generic utility class that provides various utilities and convenience methods.
 */
public final class Utils {

    /**
     * Creates a {@link String}-based tag from a given {@link Class} that can be used as a tag in
     * logging methods of {@link android.util.Log}.
     * @param loggedClass A {@link Class}.
     * @return A created tag as a {@link String}.
     */
    public static String tag(final Class<?> loggedClass) {
        return loggedClass.getSimpleName();
    }
}
