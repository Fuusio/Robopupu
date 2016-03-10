/*
 * Copyright (C) 2000-2014 Marko Salmela.
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

import com.robopupu.api.dependency.ScopedObject;

/**
 * {@link AbstractPluginComponent} provides an abstract base class for implementing
 * {@link PluginComponent}s.
 */
public class AbstractPluginComponent extends ScopedObject
        implements PluginComponent {

    protected AbstractPluginComponent() {
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        // By default do nothing
    }

    @Override
    public void onUnplugged(final PluginBus bus) {
        // By default do nothing
    }

    @Override
    public void onPluginPlugged(final Object plugin) {
        // By default do nothing
    }

    @Override
    public void onPluginUnplugged(final Object plugin) {
        // By default do nothing
    }
}
