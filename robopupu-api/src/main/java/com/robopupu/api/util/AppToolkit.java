package com.robopupu.api.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;

/**
 * {@link AppToolkit} provides a set of {@link android.app.Application} related utility methods.
 */
public final class AppToolkit {

    private final static String TAG = Utils.tag(AppToolkit.class);
    
    @SuppressWarnings("unchecked")
    public static <T extends Application> T getApplication(final Context context) {
        return (T) context.getApplicationContext();
    }

    /**
     * Gets application directory using the given {@link Context}.
     *
     * @param context A {@link Context}.
     * @return The directory as a {@link File}. If accessing the directory fails, {@code null} is
     * returned.
     */
    public static File getApplicationDirectory(final Context context) {
        final String path = getApplicationDirectoryPath(context);
        final File directory = new File(path);

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.e(TAG, "getApplicationDirectory() : Failed to create directory; " + path);
                return null;
            }
            if (!directory.canRead()) {
                Log.e(TAG, "getApplicationDirectory() : Cannot read directory: " + path);
                return null;
            }
        }
        return directory;
    }

    /**
     * Gets application directory path using the given {@link Context}.
     *
     * @param context A {@link Context}.
     * @return The directory path as a {@link String}. If accessing the directory path fails,
     * an empty {@code String} is returned.
     */
    @NonNull
    public static String getApplicationDirectoryPath(final Context context) {
        final Application application = getApplication(context);
        final PackageManager manager = application.getPackageManager();
        final String packageName = application.getPackageName();

        try {
            final PackageInfo info = manager.getPackageInfo(packageName, 0);
            return info.applicationInfo.dataDir;
        } catch (final PackageManager.NameNotFoundException e) {
            Log.d(TAG, "getApplicationDirectoryPath : Error Package name not found.");
        }
        return "";
    }

    /**
     * Tests if the device has NFC capability.
     * @return A {@code boolean}.
     */
    public static boolean hasNfc(final Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            final NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
            final NfcAdapter adapter = manager.getDefaultAdapter();
            return (adapter != null && adapter.isEnabled());
        }
        return false;
    }
}
