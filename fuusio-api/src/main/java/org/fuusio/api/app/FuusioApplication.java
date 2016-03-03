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
package org.fuusio.api.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

import org.fuusio.api.dependency.AppDependencyScope;
import org.fuusio.api.dependency.DependenciesCache;
import org.fuusio.api.dependency.Dependency;
import org.fuusio.api.util.L;
import org.fuusio.api.util.UIToolkit;

import java.io.File;

public abstract class FuusioApplication extends Application {

    private static final String TAG = FuusioApplication.class.getSimpleName();

    private static FuusioApplication sInstance = null;

    protected final AppDependencyScope mAppScope;
    protected final DependenciesCache mDependenciesCache;

    protected FuusioApplication() {
        setInstance(this);

        UIToolkit.setApplication(this);

        mDependenciesCache = createDependencyScopeCache();

        mAppScope = createAppScope();
        mAppScope.initialize();
        Dependency.setAppScope(mAppScope);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (usesSharedPreferences()) {
            readPreferences();
        }
        configureApplication();
    }

    public DependenciesCache getDependenciesCache() {
        return mDependenciesCache;
    }

    /**
     * This methods should be overridden by the subclasses to configure the application. e.g., by
     * instantiating the plugin components.
     */
    protected void configureApplication() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends FuusioApplication> T getInstance() {
        return (T) sInstance;
    }

    private static void setInstance(final FuusioApplication instance) {
        sInstance = instance;
    }

    protected abstract AppDependencyScope createAppScope();

    protected DependenciesCache createDependencyScopeCache() {
        return new DependenciesCache();
    }

    /**
     * Return the Google Analytics Property ID.
     *
     * @return The property ID as an {@code int} value.
     */
    public int getAnalyticsPropertyId() {
        return -1;
    }

    /**
     * Return the {@link Resources}.
     *
     * @return A {@link Resources}.
     */
    public static Resources getApplicationResources() {
        return sInstance.getResources();
    }

    /**
     * Return the {@link SharedPreferences}.
     *
     * @return A {@link SharedPreferences}.
     */
    public SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    /**
     * Test if this {@link FuusioApplication} uses {@link SharedPreferences}. By default an
     * {@link FuusioApplication} does not use them. Override in your own extended application class
     * if {@link SharedPreferences} are to be used.
     * @return A {@link boolean}.
     */
    protected boolean usesSharedPreferences() {
        return false;
    }

    /**
     * Read the application preferences.
     */
    protected final void readPreferences() {
        final SharedPreferences preferences = getPreferences();
        onReadPreferences(preferences);
    }

    /**
     * Invoked by {@link FuusioApplication#readPreferences()}. This method should be overridden
     * in extended classes.
     *
     * @param preferences A {@link SharedPreferences} for reading the preferences.
     */
    protected void onReadPreferences(final SharedPreferences preferences) {
        // Do nothing by default
    }

    /**
     * Write the application preferences.
     */
    public final void writePreferences() {
        final SharedPreferences preferences = getPreferences();
        final SharedPreferences.Editor editor = preferences.edit();
        onWritePreferences(preferences);
        final boolean committed = editor.commit();

        if (!committed) {
            Log.e(TAG, "writePreferences() :  Committing the preferences failed.");
        }
    }

    /**
     * Invoked by {@link FuusioApplication#writePreferences()}. This method should be overridden
     * in extended classes.
     *
     * @param preferences A {@link SharedPreferences} for reading the preferences.
     */
    protected void onWritePreferences(final SharedPreferences preferences) {
        // Do nothing by default
    }

    @SuppressWarnings("unchecked")
    public static <T extends FuusioApplication> T getApplication(final Activity activity) {
        return (T) activity.getApplicationContext();
    }

    /**
     * Gets application directory.
     * @return The directory as a {@link File}. If accessing the directory fails, {@code null} is
     * returned.
     */
    public static File getApplicationDirectory() {
        final String path = getApplicationDirectoryPath();
        final File directory = new File(path);

        assert (directory != null);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        assert (directory.exists() && directory.canRead());

        return directory;
    }

    /**
     * Gets application directory path.
     * @return The directory path as a {@link File}. If accessing the directory fails, {@code null}
     * is returned.
     */
    public static String getApplicationDirectoryPath() {
        final Application application = getInstance();
        final PackageManager manager = application.getPackageManager();
        final String packageName = application.getPackageName();

        try {
            final PackageInfo info = manager.getPackageInfo(packageName, 0);
            return info.applicationInfo.dataDir;
        } catch (final PackageManager.NameNotFoundException e) {
            Log.d(TAG, "getApplicationDirectoryPath : Error Package name not found ");
        }
        return null;
    }
}
