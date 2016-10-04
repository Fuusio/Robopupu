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
import org.mockito.Mockito;

public class TestFeatureScope extends DependencyScope {

    private boolean wasDisposed;

    public TestFeatureScope() {
    }

    public final boolean wasDisposed() {
        return wasDisposed;
    }

    public void setWasDisposed(final boolean pDisposed) {
        wasDisposed = pDisposed;
    }

    @Override
    protected void dispose() {
        super.dispose();
        wasDisposed = true;
    }

    @Override
    protected <T> T getDependency() {

        if (type(TestView.class)) {
            return dependency(Mockito.mock(TestView.class));
        } else if (type(TestPresenter.class)) {
            return dependency(Mockito.mock(TestPresenter.class));
        }
        return null;
    }

    public boolean isCleared() {
        return dependants.isEmpty() && dependencies.isEmpty();
    }
}