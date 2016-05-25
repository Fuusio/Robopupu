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
package com.robopupu.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Network;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;

import com.robopupu.app.RobopupuApplication;

import com.robopupu.api.component.Manager;
import com.robopupu.api.plugin.PlugInterface;

import java.io.File;
import java.util.List;

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
     * Gets the {@link PackageInfo} of the application.
     *
     * @return A {@link PackageInfo}.
     */
    PackageInfo getPackageInfo();

    /**
     * Gets application directory.
     * @return The directory as a {@link File}. If accessing the directory fails, {@code null} is
     * returned.
     */
    File getApplicationDirectory();

    /**
     * Gets application directory path.
     * @return The directory path as a {@link File}. If accessing the directory fails, {@code null}
     * is returned.
     */
    String getApplicationDirectoryPath();

    /**
     * Tests if the specified package is installed on the device.
     * @param packageName The package name as a {@link String}.
     * @return A {@code boolean} value.
     */
    boolean isPackageInstalled(final String packageName);

    /**
     * Tests if NFC is available on the device.
     * @return A {@code boolean} value.
     */
    boolean hasNfc();

    /**
     * Tests if network is available.
     * @return A {@code boolean} value.
     */
    // boolean isNetworkAvailable();

    /**
     * Gets a list of available networks.
     * @return A {@link List} of {@link Network}s.
     */
    // List<Network> getAvailableNetworks();

    /**
     * Gets the specified color value.
     * @param colorResId A color resource id.
     * @return The int value for the color.
     */
    @ColorInt int getColor(@ColorRes int colorResId);

    /**
     * Gets the specified integer ressource.
     * @param intResId The resource id of the integer.
     * @return An {@code int} value.
     */
    int getInteger(@IntegerRes int intResId);

    /**
     * Gets the specified string resource formatted with the given optional arguments.
     * @param stringResId The resource id of the string.
     * @param formatArgs Optional formatting arguments.
     * @return A {@link String}.
     */
    String getString(@StringRes int stringResId, final Object... formatArgs);

    /**
     * Exists the application.
     */
    void exitApplication();
}
