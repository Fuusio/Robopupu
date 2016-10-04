/*
 * Copyright (C) 2016 Marko Salmela, http://floxp.com
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

import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.robopupu.api.component.AbstractManager;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.util.AppToolkit;
import com.robopupu.api.util.StringToolkit;
import com.robopupu.app.RobopupuAppScope;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * {@link FileManagerImpl} provides a default implementation of {@link FileManager}.
 */
@Plugin
public class FileManagerImpl extends AbstractManager implements FileManager {
    private static final String TAG = FileManagerImpl.class.getSimpleName();
    private static final Pattern DIR_SEPARATOR = Pattern.compile("/");

    private Gson gson;
    private File appDirectory;
    private File externalAppDirectory;
    private File externalStorageDirectory;

    @Plug AppManager appManager;
    @Plug PlatformManager platformManager;

    @Scope(RobopupuAppScope.class)
    @Provides(FileManager.class)
    public FileManagerImpl() {
    }

    @Override
    public File getAppDirectory() {
        if (appDirectory == null) {
            appDirectory = AppToolkit.getApplicationDirectory(platformManager.getAppContext());
        }
        return appDirectory;
    }

    @Override
    public File getExternalAppDirectory() {
        if (externalAppDirectory == null) {
            final File externalStorageDirectory = getExternalStorageDirectory();
            final File dataDirectory = new File(externalStorageDirectory, "Android/data");

            if (!dataDirectory.exists()) {
                dataDirectory.mkdirs();
            }

            final PackageInfo packageInfo = appManager.getPackageInfo();
            externalAppDirectory = new File(externalStorageDirectory, packageInfo.packageName);

            if (!externalAppDirectory.exists()) {
                externalAppDirectory.mkdirs();
            }
        }
        return externalAppDirectory;
    }

    @Override
    public File getExternalStorageDirectory() {
        if (externalStorageDirectory == null) {
            final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            externalStorageDirectory = new File(path);
        }
        return externalStorageDirectory;
    }

    @Override
    public File getFile(final String filePath) {
        return getFile(filePath, false);
    }

    @Override
    public File getFile(final String filePath, final boolean isReadOnly) {
        final File file = new File(filePath);

        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    return null;
                }
            } catch (IOException e) {
                Log.e(TAG, "getFile() : Failed to create File: " + filePath);
                return null;
            }
        }

        if (file.canRead() && (isReadOnly || file.canWrite())) {
            return file;
        }
        return null;
    }

    @Override
    public boolean removeFile(final String filePath) {
        return removeFile(new File(filePath));
    }

    @Override
    public boolean removeFile(final File file) {
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    @Override
    public boolean renameFile(final File file, final String newName) {
        if (file.exists()) {
            if (newName.indexOf('/') >= 0) {
                return renameFile(file, new File(newName));
            } else {
                final File newPath = getFile(file.getParentFile().getAbsolutePath() + '/' + newName);

                if (newPath != null) {
                    return file.renameTo(newPath);
                }
            }
        }
        return false;
    }

    @Override
    public boolean renameFile(final File file, final File newPath) {
        if (file.exists()) {
            return file.renameTo(newPath);
        }
        return false;
    }



    /**
     * Raturns all available SD-Cards in the system (include emulated)
     *
     * Warning: Hack! Based on Android source code of version 4.3 (API 18)
     * Because there is no standart way to get it.
     * TODO: Test on future Android versions 4.4+
     *
     * @return paths to all available SD-Cards in the system (include emulated)
     */
    public static String[] getStorageDirectories()
    {
        final Set<String> storageDirectories = new HashSet<>();
        final String rawExternalStorage = System.getenv("EXTERNAL_STORAGE");
        final String rawSecondaryStoragesList = System.getenv("SECONDARY_STORAGE");
        final String rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET");

        if (StringToolkit.isEmpty(rawEmulatedStorageTarget))  {
            if (StringToolkit.isEmpty(rawExternalStorage)) {
                storageDirectories.add("/storage/sdcard0");
            } else {
                storageDirectories.add(rawExternalStorage);
            }
        } else {
            final String rawUserId;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                rawUserId = "";
            } else {
                final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                final String[] folders = DIR_SEPARATOR.split(path);
                final String lastFolder = folders[folders.length - 1];
                boolean isDigit = false;

                try {
                    Integer.valueOf(lastFolder);
                    isDigit = true;
                } catch(NumberFormatException ignored) {
                }
                rawUserId = isDigit ? lastFolder : "";
            }

            if (StringToolkit.isEmpty(rawUserId)) {
                storageDirectories.add(rawEmulatedStorageTarget);
            } else {
                storageDirectories.add(rawEmulatedStorageTarget + File.separator + rawUserId);
            }
        }

        if (!StringToolkit.isEmpty(rawSecondaryStoragesList)) {
            // All Secondary SD-CARDs splited into array
            final String[] rawSecondaryStorages = rawSecondaryStoragesList.split(File.pathSeparator);
            Collections.addAll(storageDirectories, rawSecondaryStorages);
        }
        return storageDirectories.toArray(new String[storageDirectories.size()]);
    }
}
