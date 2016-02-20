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

import org.fuusio.api.dependency.ScopedObject;
import org.fuusio.api.network.volley.VolleyRequestManager;

/**
 * {@link AbstractRestService} provides an abstract base class for implementing {@link RestService).}
 */
public class AbstractRestService extends ScopedObject implements RestService {

    private VolleyRequestManager mRequestManager;

    public VolleyRequestManager getRequestManager() {
        if (mRequestManager == null) {
            mRequestManager = get(VolleyRequestManager.class);
        }
        return mRequestManager;
    }
}
