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
package org.fuusio.api.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

import org.fuusio.api.dependency.D;
import org.fuusio.api.graphics.BitmapManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AppToolkit {

    private static final String PREFIX_EDIT = "edit_";

    private static Application sApplication = null;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getApplicationContext() {
        return sApplication.getApplicationContext();
    }

    public static void setApplication(final Application application) {
        sApplication = application;
    }

    public static String getStringResource(final int resId) {
        return sApplication.getString(resId);
    }

    public static String getPropertyName(final View view) {
        final Resources resources = view.getResources();
        final String resourceName = resources.getResourceEntryName(view.getId());

        if (resourceName != null && resourceName.startsWith(PREFIX_EDIT)) {
            return resourceName.substring(PREFIX_EDIT.length());
        }
        return null;
    }

    public static boolean getBoolean(final int resId) {
        return sApplication.getResources().getBoolean(resId);
    }

    public static int getColor(final int resId) {
        return sApplication.getResources().getColor(resId);
    }

    public static int getInteger(final int resId) {
        return sApplication.getResources().getInteger(resId);
    }

    public static LocationManager getLocationManager() {
        return (LocationManager) sApplication.getSystemService(Context.LOCATION_SERVICE);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return sApplication.getResources().getDisplayMetrics();
    }

    public static String getString(final int resId) {
        return sApplication.getString(resId);
    }

    public static String getString(final int resId, final Object... formatArgs) {
        return sApplication.getString(resId, formatArgs);
    }

    public static String[] getStringArray(final int resId) {
        return sApplication.getResources().getStringArray(resId);
    }

    public static Bitmap getBitmap(final int resId) {
        return getBitmap(resId, true);
    }

    private static BitmapManager getBitmapManager() {
        return D.get(BitmapManager.class);
    }

    public static Bitmap getBitmap(final int resId, final boolean useCache) {
        Bitmap bitmap = null;

        if (useCache) {
            bitmap = getCachedBitmap(resId);
        }

        if (bitmap == null) {
            final InputStream inputStream = sApplication.getResources().openRawResource(resId);
            bitmap = BitmapFactory.decodeStream(inputStream);

            if (useCache) {
                getBitmapManager().addBitmap(resId, bitmap);
            }
        }

        return bitmap;
    }

    public static Bitmap loadBitmap(final int resId, final boolean useCache) {
        Bitmap bitmap = null;

        if (useCache) {
            bitmap = getCachedBitmap(resId);
        }

        final Context context = sApplication.getApplicationContext();

        if (bitmap == null) {
            final InputStream inputStream = context.getResources().openRawResource(resId);
            bitmap = BitmapFactory.decodeStream(inputStream);

            if (useCache) {
                getBitmapManager().addBitmap(resId, bitmap);
            }
        }

        return bitmap;
    }

    public static Bitmap loadBitmap(final String filepath) {
        Bitmap bitmap = null;

        final File file = new File(filepath);

        if (file.exists() && file.canRead()) {

            FileInputStream inputStream = null;

            try {
                inputStream = new FileInputStream(file);
            } catch (final Exception e) {
                e.printStackTrace();
            }

            if (inputStream != null) {
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
        }

        return bitmap;
    }

    public static Bitmap getCachedBitmap(final int resId) {
        return getBitmapManager().getBitmap(resId);
    }

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

    public static String getApplicationDirectoryPath() {
        final PackageManager manager = sApplication.getPackageManager();
        final String packageName = sApplication.getPackageName();

        try {
            final PackageInfo info = manager.getPackageInfo(packageName, 0);
            return info.applicationInfo.dataDir;
        } catch (final NameNotFoundException e) {
            L.w(sApplication, "getApplicationDirectoryPath", "Error Package name not found ");
        }
        return null;
    }

    public static Resources getResources() {
        return sApplication.getResources();
    }

    public static boolean hasNfc() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            final NfcManager manager = D.get(NfcManager.class);
            final NfcAdapter adapter = manager.getDefaultAdapter();
            return (adapter != null && adapter.isEnabled());
        }
        return false;
    }

    public static boolean isNetworkAvailable() {
        final ConnectivityManager manager = D.get(ConnectivityManager.class);

        if (manager == null) {
            L.wtf(AppToolkit.class, "isNetworkAvailable()", "Network access not allowed");
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

    public static List<Network> getAvailableNetworks() {
        final ArrayList<Network> availableNetworks = new ArrayList<>();
        final ConnectivityManager manager = D.get(ConnectivityManager.class);

        if (manager == null) {
            L.wtf(AppToolkit.class, "isNetworkAvailable()", "Network access not allowed");
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
    }

    public static boolean isPackageInstalled(final String packageName) {
        final PackageManager manager = D.get(PackageManager.class);
        final List<ApplicationInfo> infos = manager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (final ApplicationInfo info : infos) {
            if (packageName.equalsIgnoreCase(info.packageName)) {
                return true;
            }
        }
        return false;
    }
}
