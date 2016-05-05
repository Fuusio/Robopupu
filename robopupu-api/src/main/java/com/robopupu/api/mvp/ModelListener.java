package com.robopupu.api.mvp;

/**
 * {@link ModelListener} defines a listener interface for {@link Model}.
 */
public interface ModelListener<T_ModelEvent extends ModelEvent> {

    /**
     * Invoked when the {@link Model} has changed as specified by the given {@link T_ModelEvent}.
     *
     * @param event A {@code T_ModelEvent}.
     */
    void onModelChanged(T_ModelEvent event);
}
