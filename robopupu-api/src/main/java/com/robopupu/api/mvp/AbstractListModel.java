package com.robopupu.api.mvp;

import android.support.v7.widget.RecyclerView;

import com.robopupu.api.model.ModelEvent;
import com.robopupu.api.model.ModelObserver;

/**
 * {@link AbstractListModel} extends {@link AbstractModel} to provide an abstract base class
 * for implementing {@link ListModel}s for {@link RecyclerViewAdapter}s.
 */
public abstract class AbstractListModel<T_ModelEvent extends ModelEvent, T_ModelListener extends ModelObserver<T_ModelEvent>>
        extends AbstractModel<T_ModelEvent, T_ModelListener>
        implements ListModel {

    private RecyclerView.AdapterDataObserver observer;

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public void remove(final int position) {
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean canDismiss(final int position) {
        return false;
    }

    @Override
    public void registerAdapterDataObserver(final RecyclerView.AdapterDataObserver observer) {
        this.observer = observer;
    }

    @Override
    public void unregisterAdapterDataObserver(final RecyclerView.AdapterDataObserver observer) {
        this.observer = null;
    }

    @Override
    public void notifyDataChanged() {
        if (observer == null) {
            throw new AssertionError("observer cannot be null.");
        }
        observer.onChanged();
    }
}
