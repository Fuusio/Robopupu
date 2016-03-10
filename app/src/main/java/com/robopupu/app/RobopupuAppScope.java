/*
 * Copyright (C) 2016 Marko Salmela, http://robopupu.com
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
package com.robopupu.app;

import com.robopupu.api.app.FuusioAppScope;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;

@Scope
public class RobopupuAppScope extends FuusioAppScope<RobopupuApplication> {

    public RobopupuAppScope(final RobopupuApplication app) {
        super(app);
    }

    @Provides
    public RobopupuApplication getRobopupuApplication() {
        return getApplication();
    }
}
