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

import android.os.Bundle;
import android.view.View;

import com.robopupu.api.dependency.D;
import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.mvp.Presenter;
import com.robopupu.api.mvp.ViewFragment;

public abstract class FeatureFragment<T_Presenter extends Presenter> extends ViewFragment<T_Presenter>
    implements FeatureView {

    private Feature feature;
    private boolean newInstanceFlag;
    private DependencyScope scope;

    protected FeatureFragment() {
        newInstanceFlag = true;
    }

    protected Feature getFeature() {
        return feature;
    }

    @Override
    public void setFeature(final Feature feature) {
        this.feature = feature;
    }
    
    @Override
    public DependencyScope getScope() {
        return scope;
    }

    @Override
    public void setScope(final DependencyScope scope) {
        this.scope = scope;
    }

    @Override
    public boolean isDialog() {
        return false;
    }

    @Override
    public void onViewCreated(final android.view.View view, final Bundle inState) {
        if (!newInstanceFlag) {
            final FeatureManager featureManager = D.get(FeatureManager.class);
            featureManager.conditionallyRestartFeature(getFeature(), this);
        } else {
            newInstanceFlag = false;
        }
        super.onViewCreated(view, inState);
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
