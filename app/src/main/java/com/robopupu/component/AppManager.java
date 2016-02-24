package com.robopupu.component;

import android.content.Context;
import android.support.annotation.StringRes;

import com.robopupu.app.RobopupuApplication;

import org.fuusio.api.component.Manager;
import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface AppManager extends Manager {

    /**
     * Gets the application {@link Context}.
     *
     * @return A @link Context}.
     */
    Context getAppContext();

    /**
     * Gets the application.
     *
     * @return The application as @link RobopupuApplication}.
     */
    RobopupuApplication getApplication();

    /**
     * Gets the version code of the application.
     *
     * @return The  version code as an {@link int}.
     */
    int getAppVersionCode();

    /**
     * Gets the version name of the application.
     *
     * @return The  version name as an {@link int}.
     */
    String getAppVersionName();

    /**
     * Gets the specified string resource formatted with the given optional arguments.
     * @param resId The resource id of the string.
     * @param formatArgs Optional formatting arguments.
     * @return A {@link String}.
     */
    String getString(@StringRes int resId, final Object... formatArgs);
}
