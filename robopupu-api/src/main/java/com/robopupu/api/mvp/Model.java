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

import com.robopupu.api.util.Gsonable;
import com.robopupu.api.util.Listenable;

/**
 * {@link Model} is interface for Model components in a MVP architectural pattern implementation.
 *
 * @param <T_ModelEvent> The parametrised {@link ModelEvent} type.
 * @param <T_ModelListener> The parametrised {@link ModelListener} type.
 */
public interface Model<T_ModelEvent extends ModelEvent, T_ModelListener extends ModelListener<T_ModelEvent>>
        extends Listenable<T_ModelListener>, Gsonable {
}
