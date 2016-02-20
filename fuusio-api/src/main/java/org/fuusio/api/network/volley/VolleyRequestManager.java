/*
 * Copyright (C) 2014 - 2015 Marko Salmela.
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
package org.fuusio.api.network.volley;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.fuusio.api.component.AbstractManager;
import org.fuusio.api.dependency.D;
import org.fuusio.api.graphics.BitmapManager;
import org.fuusio.api.network.RequestManager;
import org.fuusio.api.network.RestRequest;
import org.fuusio.api.plugin.Plugin;

/**
 * {@link VolleyRequestManager} provides an abstract base class for implementing
 * a {@link RequestManager}.
 */
@Plugin
public class VolleyRequestManager extends AbstractManager implements RequestManager {

    private static final int DEFAULT_REQUEST_TIMEOUT = 5000; // milliseconds

    private BitmapManager mBitmapManager;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    public VolleyRequestManager() {
        mImageLoader = createImageLoader();
        mRequestQueue = Volley.newRequestQueue(D.get(Context.class), createUrlStack());
    }

    protected ImageLoader createImageLoader() {
        return new ImageLoader(mRequestQueue, createImageCache());
    }

    protected ImageLoader.ImageCache createImageCache() {

        mBitmapManager = D.get(BitmapManager.class);

        return new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(final String key) {
                return mBitmapManager.getBitmap(key);
            }

            @Override
            public void putBitmap(final String key, final Bitmap bitmap) {
                mBitmapManager.addBitmap(key, bitmap);
            }
        };
    }

    protected UrlStack createUrlStack() {
        return new UrlStack();
    }

    protected ImageLoader getImageLoader() {
        return mImageLoader;
    }

    protected RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    protected int getRequestTimeout() {
        return DEFAULT_REQUEST_TIMEOUT;
    }

    protected RetryPolicy createRetryPolicy() {
        return new DefaultRetryPolicy(getRequestTimeout(), DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    /**
     * Executes the given {@link RestRequest} by constructing it and by adding it to the request queue.
     *
     * @param request A {@link RestRequest}.
     */
    @Override
    public void execute(final RestRequest request) {
        request.constructVolleyRequest();

        final VolleyRequest volleyRequest = request.getPeerRequest();
        volleyRequest.setRetryPolicy(createRetryPolicy());
        getRequestQueue().add(volleyRequest);
    }

    @Override
    public void cancelRequests(final Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @Override
    public void cancelRequests(final RequestQueue.RequestFilter filter) {
        mRequestQueue.cancelAll(filter);
    }

    @Override
    public void cancelAllRequests() {
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(final Request<?> request) {
                return true;
            }
        });
    }

    @Override
    public void clearRequestCache() {
        mRequestQueue.getCache().clear();
    }
}
