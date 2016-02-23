package com.robopupu.component;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import org.fuusio.api.component.AbstractManager;
import org.fuusio.api.dependency.Provides;

/**
 * {@link AppManagerImpl} ...
 */
public class AppManagerImpl extends AbstractManager implements AppManager {

    private final Context mAppContext;

    @Provides(AppManager.class)
    public AppManagerImpl(final Context appContext) {
        mAppContext = appContext;
    }

    @Override
    public Context getAppContext() {
        return mAppContext;
    }

    /**
     * Gets the versoin code of the application.
     *
     * @return The  version code as an {@link int}.
     */
    public int getAppVersionCode() {
        final PackageManager manager = mApplicationContext.getPackageManager();
        try {
            final String packageName = mApplicationContext.getPackageName();
            return manager.getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage());
        }
        return -1;
    }

    /**
     * Gets the versoin code of the application.
     *
     * @return The  version code as an {@link int}.
     */
    public String getAppVersionName() {
        final PackageManager manager = mApplicationContext.getPackageManager();
        try {
            final String packageName = mApplicationContext.getPackageName();
            return manager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage());
        }
        return "n/a";
    }
}
