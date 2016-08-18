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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.robopupu.api.util.AbstractListenable;

public class AbstractModel<T_ModelEvent extends ModelEvent, T_ModelListener extends ModelListener<T_ModelEvent>>
        extends AbstractListenable<T_ModelListener> implements Model<T_ModelEvent, T_ModelListener> {

    protected void notifyModelChanged(final T_ModelEvent event) {
        if (event != null) {
            for (final T_ModelListener listener : getListeners()) {
                listener.onModelChanged(event);
            }
        }
    }

    protected JsonParser getJsonParser() {
        return createJsonParser();
    }

    protected JsonParser createJsonParser() {
        return new JsonParser();
    }

    protected Gson getGson() {
        return createGson();
    }

    protected Gson createGson() {
        return new Gson();
    }

    @SuppressWarnings("unchecked")
    public JsonObject toJsonObject() {
        return (JsonObject) getJsonParser().parse(toJsonString());
    }

    public String toJsonString() {
        return getGson().toJson(this);
    }
}
