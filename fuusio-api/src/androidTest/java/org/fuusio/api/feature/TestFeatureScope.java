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
import org.mockito.Mockito;

public class TestFeatureScope extends DependencyScope {

    private boolean mWasDisposed;

    public TestFeatureScope() {
    }

    public final boolean wasDisposed() {
        return mWasDisposed;
    }

    public void setWasDisposed(final boolean pDisposed) {
        mWasDisposed = pDisposed;
    }

    @Override
    protected void dispose() {
        super.dispose();
        mWasDisposed = true;
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
        return mDependants.isEmpty() && mDependencies.isEmpty();
    }
}