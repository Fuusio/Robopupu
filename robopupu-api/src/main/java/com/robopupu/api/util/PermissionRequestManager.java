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

import com.robopupu.api.component.AbstractManager;

import java.util.HashMap;

/**
 * {@link PermissionRequestManager} implements  a manager for requesting permissions using
 * {@link PermissionRequest}s.
 */
public class PermissionRequestManager extends AbstractManager
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    private final HashMap<Integer, PermissionRequest> pendingRequests;

    public PermissionRequestManager() {
        pendingRequests = new HashMap<>();
    }

    /**
     * Create an instance of {@link PermissionRequest} for requesting the specified user permission.
     * @param activity An {@link Activity} needed for
     * {@link ActivityCompat#requestPermissions(Activity, String[], int)}. May not be {@code null}.
     * @param permission A {@link String} specifying the requested permission. May not be {@code null}.
     * @param callback A {@link PermissionRequest.Callback} provided by the permission requester. May not be {@code null}.
     * @return A {@link PermissionRequest}.
     */
    public PermissionRequest createRequest(final Activity activity, final String permission, final PermissionRequest.Callback callback) {
        final PermissionRequest request = new PermissionRequest(activity, permission, callback);
        pendingRequests.put(request.getRequestId(), request);
        return request;
    }

    /**
     * Create an instance of {@link PermissionRequest} for requesting the specified user permission.
     * The created instance is committed by invoking {@link PermissionRequest#commit()}.
     * @param activity An {@link Activity} needed for
     * {@link ActivityCompat#requestPermissions(Activity, String[], int)}. May not be {@code null}.
     * @param permission A {@link String} specifying the requested permission. May not be {@code null}.
     * @param callback A {@link PermissionRequest.Callback} provided by the permission requester. May not be {@code null}.
     * @return A {@link PermissionRequest}.
     */
    public PermissionRequest commitRequest(final Activity activity, final String permission, final PermissionRequest.Callback callback) {
        final PermissionRequest request = createRequest(activity, permission, callback);
        request.commit();
        return request;
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

    @Override
    public void onRequestPermissionsResult(final int requestCode,final String[] permissions, final int[] grantResults) {
        if (pendingRequests.containsKey(requestCode)) {
            final PermissionRequest request = pendingRequests.get(requestCode);
            request.onRequestPermissionsResult(grantResults[0] == PackageManager.PERMISSION_GRANTED);
            pendingRequests.remove(requestCode);
        }
    }

    /**
     * Test if there any pending {@link PermissionRequest}s.
     * @return A {@code boolean} value.
     */
    public boolean hasPendingRequests() {
        return !pendingRequests.isEmpty();
    }

    /**
     * Clear all pending {@link PermissionRequest}s if any.
     * @return A {@code boolean} value indicating if there were any pending {@link PermissionRequest}s.
     */
    public boolean clearPendingRequests() {
        final boolean hadPendingRequests = hasPendingRequests();
        pendingRequests.clear();
        return hadPendingRequests;
    }
}
