/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.feature;

import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.mvp.Presenter;
import com.robopupu.api.mvp.ViewCompatFragment;

public abstract class FeatureCompatFragment<T_Presenter extends Presenter> extends ViewCompatFragment<T_Presenter>
    implements FeatureView {

    private Feature mFeature;
    private DependencyScope mScope;

    protected FeatureCompatFragment() {
    }

    @Override
    public void setFeature(final Feature feature) {
        mFeature = feature;
    }
    
    @Override
    public DependencyScope getScope() {
        return mScope;
    }

    @Override
    public void setScope(final DependencyScope scope) {
        mScope = scope;
    }

    @Override
    public boolean isDialog() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mFeature != null) {
            mFeature.addActiveView(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mFeature != null) {
            mFeature.removeActiveView(this);
        }
    }
}