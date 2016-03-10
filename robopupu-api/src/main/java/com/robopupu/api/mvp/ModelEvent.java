package com.robopupu.api.mvp;

import java.util.EventObject;

/**
 * {@link ModelEvent} extends {@link  EventObject} to provide an abstract base class for implementing
 * {@link Model} events.
 *
 * @param <T_Model> The parametrised type extending {@link Model}.
 * @param <T_Type>  The parametrised type (e.g. enum type) representing Model event types.
 */
public abstract class ModelEvent<T_Model extends Model, T_Type> extends EventObject {

    private final long mTimeStamp;
    private final T_Type mType;

    protected ModelEvent(final T_Model model, final T_Type type) {
        super(model);
        mType = type;
        mTimeStamp = System.currentTimeMillis();
    }

    @SuppressWarnings("unchecked")
    public final <T extends T_Model> T getModel() {
        return (T) getSource();
    }

    public final T_Type getType() {
        return mType;
    }

    public final long getTimeStamp() {
        return mTimeStamp;
    }
}
