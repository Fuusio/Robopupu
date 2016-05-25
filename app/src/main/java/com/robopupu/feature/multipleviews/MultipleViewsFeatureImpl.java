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
package com.robopupu.feature.multipleviews;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;
import com.robopupu.api.feature.AbstractFeature;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.app.RobopupuAppScope;
import com.robopupu.component.PlatformManager;
import com.robopupu.feature.multipleviews.view.MultipleViewsActivity;


@Plugin
public class MultipleViewsFeatureImpl extends AbstractFeature implements MultipleViewsFeature {

    @Plug PlatformManager mPlatformManager;

    @Scope(RobopupuAppScope.class)
    @Provides(MultipleViewsFeature.class)
    public MultipleViewsFeatureImpl() {
        super(MultipleViewsFeatureScope.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPlatformManager.startActivity(MultipleViewsActivity.class);
    }
}
