package com.robopupu.component;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.StringRes;
import android.util.Log;

import com.robopupu.app.RobopupuAppScope;
import com.robopupu.app.RobopupuApplication;

import org.fuusio.api.component.AbstractManager;
import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class AppManagerImpl extends AbstractManager implements AppManager {

    private static final String TAG = AppManagerImpl.class.getSimpleName();

    private final RobopupuApplication mApplication;

    @Scope(RobopupuAppScope.class)
    @Provides(AppManager.class)
    public AppManagerImpl(final RobopupuApplication application) {
        mApplication = application;
    }

    @Override
    public RobopupuApplication getApplication() {
        return mApplication;
    }

    @Override
    public Context getAppContext() {
        return mApplication.getApplicationContext();
    }

    @Override
    public int getAppVersionCode() {
        final Context context = mApplication.getApplicationContext();
        final PackageManager manager = context.getPackageManager();
        try {
            final String packageName = context.getPackageName();
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
        final Context context = mApplication.getApplicationContext();
        final PackageManager manager = context.getPackageManager();
        try {
            final String packageName = context.getPackageName();
            return manager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage());
        }
        return "n/a";
    }

    @Override
    public String getString(final @StringRes int resId, final Object... formatArgs) {
        return mApplication.getString(resId, formatArgs);
    }
}
