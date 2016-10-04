package com.robopupu.api.model;

import com.robopupu.api.mvp.Model;

/**
 * {@link ModelObserver} defines a listener interface for {@link Model}.
 */
public interface ModelObserver<T_ModelEvent extends ModelEvent> {

    /**
     * Invoked when the observer {@link Model} has changed as specified by the given {@code ModelEvent}.
     *
     * @param event A {@code ModelEvent}.
     */
    void onModelChanged(T_ModelEvent event);
}
