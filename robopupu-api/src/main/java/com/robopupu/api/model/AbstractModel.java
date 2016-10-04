package com.robopupu.api.model;

import com.robopupu.api.util.GsonObject;

/**
 * {@link AbstractModel} provides an abstract base class for implementing {@link Model}s that
 * can be serialised using GSON.
 */
public class AbstractModel<T> extends GsonObject<T> implements Model {
}
