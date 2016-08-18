/*
 * Copyright (C) 2000-2015 Marko Salmela.
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
package com.robopupu.api.plugin;

/**
 * {@link HandlerInvoker} provides an abstract base class for implementing an invocation delegate
 * that uses a {@link android.os.Handler} for synchronising the invocation to main thread.
 */
public abstract class HandlerInvoker<T> {

    protected T plugin;

    public void setPlugin(final T plugin) {
        this.plugin = plugin;
    }
}
