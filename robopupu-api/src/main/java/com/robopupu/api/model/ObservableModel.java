package com.robopupu.api.model;

import com.robopupu.api.util.Listenable;

/**
 * {@link ObservableModel} defines an interface for model objects that can be serialised using GSON.
 */

public interface ObservableModel<T_Event extends ModelEvent<?,?>, T_Observer extends ModelObserver<T_Event>>
        extends Model, Listenable<T_Observer> {
}
