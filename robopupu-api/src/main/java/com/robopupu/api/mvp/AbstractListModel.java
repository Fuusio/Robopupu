package com.robopupu.api.mvp;

import android.support.v7.widget.RecyclerView;

/**
 * {@link AbstractListModel} extends {@link AbstractModel} to provide an abstract base class
 * for implementing {@link ListModel}s for {@link RecyclerViewAdapter}s.
 */
public abstract class AbstractListModel<T_EventType, T_Listener extends Model.Listener>
        extends AbstractModel<T_EventType, T_Listener>
        implements ListModel<T_EventType, T_Listener> {

    private RecyclerView.AdapterDataObserver mObserver;

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
        mObserver = observer;
    }

    @Override
    public void unregisterAdapterDataObserver(final RecyclerView.AdapterDataObserver observer) {
        mObserver = null;
    }

    @Override
    public void notifyDataChanged() {
        if (mObserver == null) {
            throw new AssertionError("mObserver cannot be null.");
        }
        mObserver.onChanged();
    }
}
