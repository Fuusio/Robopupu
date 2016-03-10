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

public class AbstractModel<T_EventType, T_Listener extends Model.Listener>
        extends AbstractListenable<T_Listener> implements Model<T_EventType, T_Listener> {

    protected ModelEvent createEvent(final T_EventType type) {
        return null;
    }

    protected void notifyModelChanged(final T_EventType type) {
        final ModelEvent event = createEvent(type);

        if (event != null) {
            for (final Listener listener : getListeners()) {
                listener.onModelChanged(event);
            }
        }
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonParser jsonParser = new JsonParser();
        return (JsonObject) jsonParser.parse(toJsonString());
    }

    @Override
    public String toJsonString() {
        final Gson gson = new Gson();
        return gson.toJson(this);
    }
}
