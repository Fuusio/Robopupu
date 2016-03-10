/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.graphics;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.robopupu.api.plugin.PlugInterface;

@PlugInterface
public interface BitmapManager {

    Bitmap getBitmap(int resId);

    Bitmap getBitmap(String cacheName, int resId);

    Bitmap getBitmap(String key);

    LruCache<String, Bitmap> addCache(String cacheName);

    LruCache<String, Bitmap> addCache(String cacheName, int cacheSize);

    void clearCache();

    void clearCache(String cacheName);

    int getCacheSize();

    int getCacheSize(String cacheName);

    void resizeCache(String cacheName, int newSize);

    void removeCache(String cacheName);

    void clearAllCaches();

    void dispose();

    void addBitmap(int resId, Bitmap bitmap);

    void addBitmap(String key, Bitmap bitmap);

    void addBitmap(int resId, String cacheName, Bitmap bitmap);

    void addBitmap(String key, String cacheName, Bitmap bitmap);
}
