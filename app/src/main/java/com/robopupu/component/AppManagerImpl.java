/*
 * Copyright (C) 2016 Marko Salmela, http://fuusio.org
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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.robopupu.R;
import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.app.RobopupuAppScope;
import com.robopupu.app.RobopupuApplication;

import org.fuusio.api.component.AbstractManager;
import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

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

    @Override
    public void startActivity(final Intent intent) {
        final Activity activity = mFeatureManager.getForegroundActivity();
        activity.startActivity(intent);
    }
}
