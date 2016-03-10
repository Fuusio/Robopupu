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
package com.robopupu.api.mvp;

import com.robopupu.api.util.Params;

/**
 * {@link Presenter} is the interface to be implemented by Presenter components of a MVP
 * architectural pattern implementation. By default, a {@link Presenter} implementation is also
 * a listener for {@link View} lifecycle events via the {@link ViewListener} interface.
 */
public interface Presenter extends ViewListener {

    /**
     * Gets the {@link View} attached to this {@link Presenter}.
     *
     * @return A {@link View}.
     */
    View getView();

    /**
     * Sets {@link Params} for this {@link Presenter},
     * @param params A {@link Params}.
     */
    void setParams(Params params);

    /**
     * Invoked to finish this {@link Presenter}.
     */
    void finish();
}
