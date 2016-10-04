package com.robopupu.api.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.robopupu.api.util.AbstractListenable;
import com.robopupu.api.util.GsonObject;
import com.robopupu.api.util.Gsonable;

/**
 * {@link AbstractObservableModel} provides an abstract base class for implementing
 * {@link ObservableModel}s and that can be serialised using GSON.
 */
public abstract class AbstractObservableModel<T_Event extends ModelEvent<?,?>, T_Observer extends ModelObserver<T_Event>>
        extends AbstractListenable<T_Observer>
        implements Gsonable, ObservableModel<T_Event, T_Observer> {

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

    protected void notifyModelChanged(final T_Event event) {
        if (event != null) {
            for (final T_Observer observer : getListeners()) {
                observer.onModelChanged(event);
            }
        }
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
