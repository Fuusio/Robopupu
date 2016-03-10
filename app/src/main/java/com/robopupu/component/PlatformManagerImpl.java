/*
 * Copyright (C) 2016 Marko Salmela, http://robopupu.com
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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.StringRes;

import com.robopupu.app.RobopupuAppScope;

import com.robopupu.api.component.AbstractManager;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;

@Plugin
public class PlatformManagerImpl extends AbstractManager implements PlatformManager {

    private static final String TAG = PlatformManagerImpl.class.getSimpleName();

    @Plug AppManager mAppManager;

    @Scope(RobopupuAppScope.class)
    @Provides(PlatformManager.class)
    public PlatformManagerImpl() {
    }

    @Override
    public String getString(final @StringRes int stringResId, final Object... formatArgs) {
        return mAppManager.getString(stringResId, formatArgs);
    }

    @Override
    public void openWebPage(final String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        mAppManager.startActivity(intent);
    }

    @Override
    public boolean sendEmail(final String address, final String subject, final String body, final String chooserTitle) {
        final StringBuilder uri = new StringBuilder("mailto:");
        uri.append(Uri.encode(address));
        uri.append("?subject=");
        uri.append(Uri.encode(subject));
        uri.append("&body=");
        uri.append(Uri.encode(body));

        final Intent sendIntent = new Intent(android.content.Intent.ACTION_SENDTO);
        sendIntent.setType("message/rfc822");
        sendIntent.setData(Uri.parse(uri.toString()));

        try {
            mAppManager.startActivity(Intent.createChooser(sendIntent, chooserTitle));
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}
