package com.robopupu.api.mvp;

import java.util.EventObject;

/**
 * {@link ModelEvent} extends {@link  EventObject} to provide an abstract base class for implementing
 * {@link Model} related events.
 *
 * @param <T_Model> The parametrised eventType extending {@link Model}.
 * @param <T_EventType>  The parametrised eventType (e.g. enum eventType) representing Model event types.
 */
public abstract class ModelEvent<T_Model extends Model, T_EventType> extends EventObject {

    private final T_EventType eventType;

    protected ModelEvent(final T_Model model, final T_EventType eventType) {
        super(model);
        this.eventType = eventType;
    }

    @SuppressWarnings("unchecked")
    public final <T extends T_Model> T getModel() {
        return (T) getSource();
    }

    public final T_EventType getEventType() {
        return eventType;
    }
}
