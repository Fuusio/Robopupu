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

import com.robopupu.api.component.Manager;
import com.robopupu.api.plugin.PlugInterface;

import java.io.File;

/**
 * {@link FileManager} defines a plug interface for providing file system related services.
 */
@PlugInterface
public interface FileManager extends Manager {

    File getAppDirectory();

    File getExternalAppDirectory();

    File getExternalStorageDirectory();

    File getFile(String filePath);

    File getFile(String filePath, boolean isReadOnly);

    boolean removeFile(String filePath);

    boolean removeFile(File file);

    boolean renameFile(File file, String newName);

    boolean renameFile(File file, File newPath);
}
