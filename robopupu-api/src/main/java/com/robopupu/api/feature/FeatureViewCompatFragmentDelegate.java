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

import android.support.v4.app.Fragment;

import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.mvp.Presenter;
import com.robopupu.api.mvp.ViewCompatFragmentDelegate;

public abstract class FeatureViewCompatFragmentDelegate<T_Presenter extends Presenter, T_Fragment extends Fragment>
        extends ViewCompatFragmentDelegate<T_Presenter, T_Fragment>
    implements FeatureView {

    private Feature feature;

    protected FeatureViewCompatFragmentDelegate(final T_Fragment fragment) {
        super(fragment);
    }

    @Override
    public void setFeature(final Feature feature) {
        this.feature = feature;
    }
    
    @Override
    public boolean isDialog() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (feature != null) {
            feature.addActiveView(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (feature != null) {
            feature.removeActiveView(this);
        }
    }
}
