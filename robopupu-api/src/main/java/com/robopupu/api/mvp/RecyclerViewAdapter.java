package com.robopupu.api.mvp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.AbsListView;

/**
 * {@link RecyclerViewAdapter} provides and abstract base class for implementing
 * .{@link RecyclerView.Adapter}
 */
public abstract class RecyclerViewAdapter<T_ViewHolder extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<T_ViewHolder>
        implements AbsListView.OnScrollListener, ViewHolderListener {

    public static final UndoItem UNDO_ITEM = new UndoItem();

    private Context context;
    private ListModel listModel;
    private RecyclerView.AdapterDataObserver observer;
    private int undoPosition;

    protected RecyclerViewAdapter(final Context context) {
        this.context = context;
        undoPosition = -1;
    }

    public final Context getContext() {
        return context;
    }

    public void setListModel(final ListModel listModel) {
        this.listModel = listModel;

        if (this.listModel != null && observer != null) {
            this.listModel.registerAdapterDataObserver(observer);
        }
    }

    @Override
    public void registerAdapterDataObserver(final RecyclerView.AdapterDataObserver observer) {
        if (observer == null) {
            return;
        }
        super.registerAdapterDataObserver(observer);

        this.observer = observer;

        if (listModel != null) {
            listModel.registerAdapterDataObserver(observer);
        }
        notifyDataSetChanged();
    }

    @Override
    public void unregisterAdapterDataObserver(final RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        this.observer = null;

        if (listModel != null) {
            listModel.unregisterAdapterDataObserver(observer);
        }
    }

    @Override
    public int getItemCount() {
        return listModel.getItemCount();
    }

    public Object getItem(final int position) {

        if (position == undoPosition) {
            return UNDO_ITEM;
        } else {
            return listModel.getItem(position);
        }
    }

    @Override
    public long getItemId(final int position) {
        return listModel.getItemId(position);
    }

    @Override
    public int getItemViewType(final int position) {
        return 0;
    }

    @Override
    public abstract T_ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType);

    @Override
    public void onBindViewHolder(final T_ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder)holder;
        listModel.updateViewHolder(viewHolder, position);
        viewHolder.setItemPosition(position);
        viewHolder.setListener(this);
    }

    public final int getUndoPosition() {
        return undoPosition;
    }

    public void remove(final int position) {

        int newUndoPosition = position;

        if (undoPosition >= 0) {
            commitRemove();

            if (undoPosition < newUndoPosition) {
                newUndoPosition--;
            }
        }

        undoPosition = newUndoPosition;
        notifyDataSetChanged();
    }

    public boolean canDismiss(final int position) {
        return listModel.canDismiss(position);
    }

    public void undoRemove() {
        if (undoPosition >= 0) {
            undoPosition = -1;
            notifyDataSetChanged();
        }
    }

    public void commitRemove() {
        if (undoPosition >= 0) {
            listModel.remove(undoPosition);
            undoPosition = -1;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        if (undoPosition >= 0) {
            commitRemove();
        }
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        if (undoPosition >= 0) {
            commitRemove();
        }
    }

    public static class UndoItem {

        private UndoItem() {
        }
    }
}

