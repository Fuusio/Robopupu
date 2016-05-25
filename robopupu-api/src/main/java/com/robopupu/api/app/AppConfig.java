package com.robopupu.api.app;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IntegerRes;

import com.robopupu.api.dependency.D;

import java.util.HashMap;

/**
 * {@link AppConfig} can be used to check is some feature is flagged to be part of
 * an application or not.
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

    private static final HashMap<Integer, Flag> sFlags = new HashMap<>();

    private static Context sAppContext = null;

    private static Context getAppContext() {
        if (sAppContext == null) {
            sAppContext = D.get(Context.class);
        }
        return sAppContext;
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
        Flag flag = sFlags.get(flagResId);

        if (flag == null) {
            final int value = resources.getInteger(flagResId);
            flag = Flag.getValue(value);
            sFlags.put(flagResId, flag);
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
            sFlags.put(flagResId, enabled ? Flag.ENABLED_UNLOCKED : Flag.DISABLED_UNLOCKED);
        }
    }
}
