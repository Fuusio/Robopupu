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
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.robopupu.R;
import com.robopupu.api.component.AbstractManager;
import com.robopupu.api.dependency.D;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;
import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.util.AppToolkit;
import com.robopupu.app.RobopupuAppScope;
import com.robopupu.app.RobopupuApplication;

import java.io.File;
import java.util.List;

@Plugin
public class AppManagerImpl extends AbstractManager implements AppManager {

    private static final String TAG = AppManagerImpl.class.getSimpleName();

    private final RobopupuApplication mApplication;

    @Plug ExitObserver mExitObserver;
    @Plug PluginFeatureManager mFeatureManager;

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
        final PackageInfo info = getPackageInfo();

        if (info != null) {
            return info.versionCode;
        } else {
            return -1;
        }
    }

    @Override
    public String getAppVersionName() {
        final PackageInfo info = getPackageInfo();

        if (info != null) {
            return info.versionName;
        } else {
            return "n/a";
        }
    }

    @Override
    public PackageInfo getPackageInfo() {
        final Context context = mApplication.getApplicationContext();
        final PackageManager manager = context.getPackageManager();
        try {
            final String packageName = context.getPackageName();
            return manager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    public boolean isPackageInstalled(final String packageName) {
        final Context context = mApplication.getApplicationContext();
        final PackageManager manager = context.getPackageManager();
        final List<ApplicationInfo> infos = manager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (final ApplicationInfo info : infos) {
            if (packageName.equalsIgnoreCase(info.packageName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasNfc() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            final NfcManager manager = D.get(NfcManager.class);
            final NfcAdapter adapter = manager.getDefaultAdapter();
            return (adapter != null && adapter.isEnabled());
        }
        return false;
    }

    /*
    @Override
    public boolean isNetworkAvailable() {
        final ConnectivityManager manager = D.get(ConnectivityManager.class);

        if (manager == null) {
            Log.e(TAG, "isNetworkAvailable() : Network access not allowed");
        } else {
            final Network[] networks = manager.getAllNetworks();

            if (networks != null) {
                for (final Network network : networks) {
                    final NetworkInfo info = manager.getNetworkInfo(network);
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<Network> getAvailableNetworks() {
        final ArrayList<Network> availableNetworks = new ArrayList<>();
        final ConnectivityManager manager = D.get(ConnectivityManager.class);

        if (manager == null) {
            Log.e(TAG, "isNetworkAvailable() : Network access not allowed");
        } else {
            final Network[] networks = manager.getAllNetworks();

            if (networks != null) {
                for (final Network network : networks) {
                    final NetworkInfo info = manager.getNetworkInfo(network);
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        availableNetworks.add(network);
                    }
                }
            }
        }
        return availableNetworks;
    }*/

    @Override
    public File getApplicationDirectory() {
        return AppToolkit.getApplicationDirectory(getAppContext());
    }

    @Override
    public String getApplicationDirectoryPath() {
        return AppToolkit.getApplicationDirectoryPath(getAppContext());
    }

    @Override
    public @ColorInt int getColor(@ColorRes int colorResId) {
        return ContextCompat.getColor(mApplication, colorResId);
    }

    @Override
    public int getInteger(@IntegerRes int intResId) {
        return mApplication.getResources().getInteger(intResId);
    }

    @Override
    public String getString(final @StringRes int stringResId, final Object... formatArgs) {
        return mApplication.getString(stringResId, formatArgs);
    }

    @Override
    public void exitApplication() {
        final Activity activity = mFeatureManager.getForegroundActivity();
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(R.string.ft_main_dialog_title_exit_confirmation);
        alertDialog.setMessage(getString(R.string.ft_main_dialog_prompt_exit_app));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                        mExitObserver.onAppExit();
                        activity.finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int which) {
                        // Just dismiss the dialog
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
