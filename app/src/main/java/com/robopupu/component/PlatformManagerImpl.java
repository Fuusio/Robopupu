/*
 * Copyright (C) 2016 Marko Salmela, http://fuusio.org
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

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.StringRes;

import com.robopupu.app.RobopupuAppScope;

import org.fuusio.api.component.AbstractManager;
import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

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
}
