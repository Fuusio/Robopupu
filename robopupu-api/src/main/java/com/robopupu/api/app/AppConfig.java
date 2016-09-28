/*
 * Copyright (C) 2016 Marko Salmela, http://robopupu.com
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

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IntegerRes;
import android.support.v4.util.SparseArrayCompat;

import com.robopupu.api.dependency.D;

/**
 * {@link AppConfig} can be used to implement feature toggling i.e. can be used to check is some
 * feature is flagged to be part of an application or application variant.
 */
public class AppConfig {

    public enum Flag {
        DISABLED_UNLOCKED,
        ENABLED_UNLOCKED,
        DISABLED_LOCKED,
        ENABLED_LOCKED;

        public boolean isEnabled() {
            return this == ENABLED_LOCKED || this == ENABLED_UNLOCKED;
        }

        public static Flag getValue(final int value) {
            return values()[value];
        }

        public boolean isLocked() {
            return this == ENABLED_LOCKED || this == ENABLED_LOCKED;
        }
    }

    private static final SparseArrayCompat<Flag> flags = new SparseArrayCompat<>();

    private static Context appContext = null;

    private static Context getAppContext() {
        if (appContext == null) {
            appContext = D.get(Context.class);
        }
        return appContext;
    }

    /**
     * Tests if the specified feature is enabled or not.
     * @param context A {@link Context} for accessing the flags from constant resources.
     * @param flagResId The resource id for a constant defining a flag.
     * @return A {@code boolean} value.
     */
    public static boolean isEnabled(final Context context, final @IntegerRes int flagResId) {
        return isEnabled(context.getResources(), flagResId);
    }

    /**
     * Tests if the specified feature is enabled or not.
     * @param resources A {@link Resources} for accessing the flags from constant resources.
     * @param flagResId The resource id for a constant defining a flag.
     * @return A {@code boolean} value.
     */
    public static boolean isEnabled(final Resources resources, final @IntegerRes int flagResId) {
        Flag flag = flags.get(flagResId);

        if (flag == null) {
            final int value = resources.getInteger(flagResId);
            flag = Flag.getValue(value);
            flags.put(flagResId, flag);
        }
        return flag.isEnabled();
    }

    /**
     * Tests if the specified feature is enabled or not.
     * @param flagResId The resource id for a constant defining a flag.
     * @return A {@code boolean} value.
     */
    public static boolean isEnabled(final @IntegerRes int flagResId) {
        return isEnabled(getAppContext(), flagResId);
    }

    /**
     * Sets the specified feature is enabled or disabled according to given {@code boolean} value.
     * Note that is not possible to modify a locked flag.
     * @param flagResId The resource id for a constant defining a flag.
     * @param enabled A {@code boolean} value.
     */
    public static void setEnabled(final @IntegerRes int flagResId, final boolean enabled) {
        final Resources resources = getAppContext().getResources();
        final int value = resources.getInteger(flagResId);
        final Flag flag = Flag.getValue(value);

        if (!flag.isLocked()) {
            flags.put(flagResId, enabled ? Flag.ENABLED_UNLOCKED : Flag.DISABLED_UNLOCKED);
        }
    }
}
