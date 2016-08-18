/*
 * Copyright (C) 2015-2016 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * {@link PermissionRequest} is an object that can be used for requesting a permission at
 * run time.
 */
public class PermissionRequest {

    private static int requestIdCounter = 0;

    private final Activity activity;
    private final Callback callback;
    private final String permission;
    private final int requestId;

    private boolean committed;

    /**
     * Constructs a new instance {@link PermissionRequest} for requesting the specified user
     * permission.
     * @param activity An {@link Activity} needed for
     * {@link ActivityCompat#requestPermissions(Activity, String[], int)}. May not be {@code null}.
     * @param permission A {@link String} specifying the requested permission. May not be {@code null}.
     * @param callback A {@link Callback} provided by the permission requester. May not be {@code null}.
     */
    public PermissionRequest(final Activity activity, final String permission, final Callback callback) {

        ExceptionToolkit.assertArgumentNotNull(activity, "activity");
        ExceptionToolkit.assertArgumentNotNull(permission, "permission");
        ExceptionToolkit.assertArgumentNotNull(callback, "callback");

        this.activity = activity;
        this.permission = permission;
        this.callback = callback;

        requestId = requestIdCounter++;
        committed = false;
    }

    /**
     * Return the requested permission.
     * @return A {@link String} specifying the permission.
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Return the request ID assigned for this {@link PermissionRequest}.
     * @return The request ID as an int value.
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * Commits this {@link PermissionRequest}. First it check if the requested user permission has
     * been already granted. If not then, it is checked if the user should be presented with
     * a rationale for granting the requested permission. If it is not necessary to show the rationale,
     * the permission is actually requested by invoking {@link ActivityCompat#requestPermissions(Activity, String[], int)}.
     */
    public void commit() {
        if (!committed) {
            if (isPermissionGranted(activity, permission)) {
                callback.onPermissionExists(this);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    callback.onShowPermissionRationale(this);
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);
                }
            }
            committed = true;
        } else {
            throw new IllegalStateException("PermissionRequest is already committed. User method recommit() after presenting the rationale to the user.");
        }
    }

    /**
     * Recommit this {@link PermissionRequest} after the user has shown the rationale for granting
     * the permission.
     */
    public void recommit() {
        if (committed) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);
        } else {
            throw new IllegalStateException("PermissionRequest needs to be first committed by invoking commit().");
        }
    }

    /**
     * Invoked by {@link PermissionRequestManager} when the permission has been granted or denied.
     * @param granted A {@code boolean} value.
     */
    protected void onRequestPermissionsResult(final boolean granted) {
        if (granted) {
            callback.onPermissionGranted(this);
        } else {
            callback.onPermissionDenied(this);
        }
    }

    /**
     * Test if the specific user permission has been already granted.
     * @param context A {@link Context}.
     * @param permission A {@link String} specifying the requested permission. May not be {@code null}.
     * @return A {@link boolean} value.
     */
    public static boolean isPermissionGranted(final Context context, final String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public interface Callback {
        void onPermissionExists(PermissionRequest request);
        void onPermissionGranted(PermissionRequest request);
        void onPermissionDenied(PermissionRequest request);
        void onShowPermissionRationale(PermissionRequest request);
    }
}
