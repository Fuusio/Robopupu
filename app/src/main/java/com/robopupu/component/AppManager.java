package com.robopupu.component;

import android.content.Context;

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
     * Gets the version code of the application.
     *
     * @return The  version code as an {@link int}.
     */
    int getAppVersionCode();

    /**
     * Gets the version code of the application.
     *
     * @return The  version code as an {@link int}.
     */
    public String getAppVersionName() {
}
