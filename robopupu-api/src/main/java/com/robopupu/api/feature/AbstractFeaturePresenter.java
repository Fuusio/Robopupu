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
import com.robopupu.api.mvp.AbstractPresenter;
import com.robopupu.api.mvp.View;

/**
 * {@link AbstractFeaturePresenter} extends {@link AbstractPresenter} to provide an abstract base class
 * for implementing {@link FeaturePresenter}s that are controlled by {@link Feature}s.
 */
public abstract class AbstractFeaturePresenter<T_View extends View> extends AbstractPresenter<T_View>
        implements FeaturePresenter {

    private Feature feature;
    private DependencyScope scope;

    @SuppressWarnings("unchecked")
    @Override
    public Feature getFeature() {
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
}
