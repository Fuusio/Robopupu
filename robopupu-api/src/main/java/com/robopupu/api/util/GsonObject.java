/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class GsonObject<T> implements Gsonable {

    protected GsonObject() {
    }

    protected Gson createGson() {
        return new Gson();
    }

    protected Gson getGson() {
        return createGson();
    }

    protected JsonParser createJsonParser() {
        return new JsonParser();
    }

    @SuppressWarnings("unchecked")
    @Override
    public JsonObject toJsonObject() {
        final JsonParser parser = createJsonParser();
        return (JsonObject) parser.parse(toJsonString());
    }

    @Override
    public String toJsonString() {
        final Gson gson = getGson();
        return gson.toJson(this);
    }

    public static <T extends GsonObject> T fromJson(final String jsonString, final Class<T> objectClass) {
        return fromJson(jsonString, objectClass, new Gson());
    }

    public static <T extends GsonObject> T fromJson(final String jsonString, final Class<T> objectClass, final Gson gson) {
        return gson.fromJson(jsonString, objectClass);
    }

    public static <T extends GsonObject> T fromJson(final JsonObject jsonObject, final Class<T> objectClass) {
        return fromJson(jsonObject, objectClass, new Gson());
    }

    public static <T extends GsonObject> T fromJson(final JsonObject jsonObject, final Class<T> objectClass, final Gson gson ) {
        return fromJson(jsonObject.toString(), objectClass, gson);
    }
}
