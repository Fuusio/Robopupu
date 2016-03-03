/*
 * Copyright (C) 2014 - 2015 Marko Salmela.
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
package org.fuusio.api.network;

import com.android.volley.RequestQueue;

import org.fuusio.api.component.Manager;

/**
 * {@link RequestManager} defines plugin interface for a {@link Manager} that is used to execute
 * {@link Request}s.
 */
public interface RequestManager extends Manager {

    /**
     * Execute the given {@link Request}.
     * @param request A {@link Request}.
     */
    void execute(Request request);

    /**
     * Cancel all pending {@link Request}s with the given tag.
     * @param tag The tag as an {@link Object}.
     */
    void cancelRequests(Object tag);

    /**
     * Cancel all pending request using the given {@link RequestQueue.RequestFilter}.
     * @param filter A {@link RequestQueue.RequestFilter}.
     */
    void cancelRequests(RequestQueue.RequestFilter filter);

    /**
     * Cancel all pending {@link Request}s.
     */
    void cancelAllRequests();

    /**
     * Clear the current request cache.
     */
    void clearRequestCache();
}
