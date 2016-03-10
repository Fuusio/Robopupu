package com.robopupu.api.mvp;

/**
 * {@link ModelEventType} implements an enumerated type for defining various
 * {@link ModelEvent} types.
 */
public enum ModelEventType {

    MODEL_CREATED,
    MODEL_REMOVED,
    MODEL_PROPERTY_CHANGED,
    CHILD_MODEL_ADDED,
    CHILD_MODEL_REMOVED;

}
