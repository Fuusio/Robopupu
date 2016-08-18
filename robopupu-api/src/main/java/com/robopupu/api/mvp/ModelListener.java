package com.robopupu.api.mvp;

/**
 * {@link ModelListener} defines a listener interface for {@link Model}.
 */
public interface ModelListener<T_ModelEvent extends ModelEvent> {

    /**
     * Invoked when the observer {@link Model} has changed as specified by the given {@code ModelEvent}.
     *
     * @param event A {@code ModelEvent}.
     */
    void onModelChanged(T_ModelEvent event);
}
