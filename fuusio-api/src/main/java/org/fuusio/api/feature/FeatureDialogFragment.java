/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://fuusio.org
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
package org.fuusio.api.feature;

import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.Scopeable;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.mvp.ViewDialogFragment;

public abstract class FeatureDialogFragment<T_Presenter extends Presenter> extends ViewDialogFragment<T_Presenter>
    implements View, Scopeable {

    private AbstractFeature mFeature;
    private DependencyScope mScope;

    protected FeatureDialogFragment() {
    }

    public void setFeature(final AbstractFeature feature) {
        mFeature = feature;
    }

    /**
     * Get a tag for this {@link FeatureDialogFragment}.
     *
     * @return A tag as a {@link String}.
     */
    public String getFragmentTag() {
        return getClass().getSimpleName();
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
