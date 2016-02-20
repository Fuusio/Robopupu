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
package org.fuusio.api.graphics;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import org.fuusio.api.util.AppToolkit;
import org.fuusio.api.util.L;
import org.fuusio.api.util.StringToolkit;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

public class BitmapManagerImpl implements BitmapManager {

    private static final int DEFAULT_CACHE_SIZE = 50;
    private static final String NAME_DEFAULT_CACHE = "_DefaultCache";

    private HashMap<String, LruCache<String, Bitmap>> mBitmacaches;

    public BitmapManagerImpl() {
        addCache(NAME_DEFAULT_CACHE);
        mBitmacaches = new HashMap<>();
    }

    public Bitmap getBitmap(final int resId) {
        return getBitmap(NAME_DEFAULT_CACHE, resId);
    }

    public Bitmap getBitmap(final String cacheName, final int resId) {
        final LruCache<String, Bitmap> cache = mBitmacaches.get(cacheName);
        final String key = Integer.toString(resId);
        return cache.get(key);
    }

    public Bitmap getBitmap(final String key) {
        final LruCache<String, Bitmap> cache = mBitmacaches.get(NAME_DEFAULT_CACHE);
        return cache.get(key);
    }

    public LruCache<String, Bitmap> addCache(final String cacheName) {
        return addCache(cacheName, DEFAULT_CACHE_SIZE);
    }

    public LruCache<String, Bitmap> addCache(final String cacheName, final int cacheSize) {
        LruCache<String, Bitmap> cache = mBitmacaches.get(cacheName);

        assert (cache == null);

        cache = new LruCache<>(cacheSize);
        mBitmacaches.put(cacheName, cache);
        return cache;
    }

    public void clearCache() {
        clearCache(NAME_DEFAULT_CACHE);
    }

    public void clearCache(final String cacheName) {
        final LruCache<String, Bitmap> cache = mBitmacaches.get(cacheName);
        final int cacheSize = cache.maxSize();

        assert (cache != null);

        cache.trimToSize(0);
        cache.trimToSize(cacheSize);
    }

    public int getCacheSize() {
        return getCacheSize(NAME_DEFAULT_CACHE);
    }

    public int getCacheSize(final String cacheName) {
        final LruCache<String, Bitmap> cache = mBitmacaches.get(cacheName);

        assert (cache != null);

        return cache.maxSize();
    }

    public void resizeCache(final String cacheName, final int newSize) {
        final LruCache<String, Bitmap> cache = mBitmacaches.get(cacheName);

        assert (cache != null);

        cache.trimToSize(newSize);
    }

    public void removeCache(final String cacheName) {
        final LruCache<String, Bitmap> cache = mBitmacaches.get(cacheName);

        assert (cache != null);

        mBitmacaches.remove(cacheName);
    }

    public void clearAllCaches() {
        for (final String key : mBitmacaches.keySet()) {
            clearCache(key);
        }
    }

    public void dispose() {
        clearAllCaches();
        mBitmacaches.clear();
    }

    public void addBitmap(final int resId, final Bitmap bitmap) {
        addBitmap(resId, NAME_DEFAULT_CACHE, bitmap);
    }

    public void addBitmap(final String key, final Bitmap bitmap) {
        addBitmap(key, NAME_DEFAULT_CACHE, bitmap);
    }

    public void addBitmap(final int resId, final String cacheName, final Bitmap bitmap) {
        final String key = Integer.toString(resId);
        addBitmap(key, cacheName, bitmap, false);
    }

    public void addBitmap(final String key, final String cacheName, final Bitmap bitmap) {
        addBitmap(key, cacheName, bitmap, true);
    }

    private void addBitmap(final String key, final String cacheName, final Bitmap bitmap, final boolean useFileCaching) {
        LruCache<String, Bitmap> cache = mBitmacaches.get(cacheName);

        if (cache == null) {
            cache = addCache(cacheName);
        }

        cache.put(key, bitmap);

        if (useFileCaching) {
            final File applicationDirectory = AppToolkit.getApplicationDirectory();
            final StringBuilder path = new StringBuilder(applicationDirectory.getAbsolutePath());
            path.append("/");
            path.append(StringToolkit.encodeFileName(cacheName));

            final File cacheDirectory = new File(path.toString());

            if (!cacheDirectory.exists()) {
                cacheDirectory.mkdirs();
            }

            assert (cacheDirectory.exists() && cacheDirectory.canWrite());

            path.append("/");
            path.append(StringToolkit.encodeFileName(key));

            try {
                final FileOutputStream outputStream = new FileOutputStream(path.toString());
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            } catch (final Exception e) {
                L.wtf(BitmapManagerImpl.class, "", e.toString());
            }
        }
    }
}
