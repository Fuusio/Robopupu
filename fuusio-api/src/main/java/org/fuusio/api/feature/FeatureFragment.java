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

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.Scopeable;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.mvp.ViewFragment;

public abstract class FeatureFragment<T_Presenter extends Presenter> extends ViewFragment<T_Presenter>
    implements FeatureView<T_Presenter>, Scopeable {

    private Feature mFeature;
    private DependencyScope mScope;

    protected FeatureFragment() {
    }

    @Override
    public Feature getFeature() {
        return mFeature;
    }

    @Override
    public void setFeature(final Feature feature) {
        mFeature = feature;
    }

    /**
     * Get a tag for this {@link FeatureFragment}.
     *
     * @return A tag as a {@link String}.
     */
    public String getFeatureTag() {
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Class<?> dependencyType) {
        return (T) D.get(mScope, dependencyType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Class<?> dependencyType, final Object dependant) {
        return (T) D.get(mScope, dependencyType, dependant);
    }

    /**
     * Gets the {@link FeatureScope} owned by the {@link Feature} that controls this
     * {@link FeatureFragment}.
     *
     * @param <T> The generic return type of {@link FeatureScope}.
     * @return A {@link FeatureScope}.
     */
    @SuppressWarnings("unchecked")
    public final <T extends Feature> FeatureScope<T> getDependencyScope() {
        return (FeatureScope<T>)mFeature.getScope();
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
