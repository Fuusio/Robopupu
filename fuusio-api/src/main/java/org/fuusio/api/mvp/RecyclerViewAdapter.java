package org.fuusio.api.mvp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.AbsListView;

/**
 * {@link RecyclerViewAdapter} .. TODO
 */
public abstract class RecyclerViewAdapter<T_ViewHolder extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<T_ViewHolder>
        implements AbsListView.OnScrollListener, ViewHolderListener {

    public static final UndoItem UNDO_ITEM = new UndoItem();

    // REMOVE private HashMap<Class<?>, T_ViewHolder> mHolderViews;

    private Context mContext;
    private ListModel mListModel;
    private RecyclerView.AdapterDataObserver mObserver;
    private int mUndoPosition;

    protected RecyclerViewAdapter(final Context context) {
        mContext = context;
        // REMOVE mHolderViews = new HashMap<>();
        mUndoPosition = -1;
    }

    public final Context getContext() {
        return mContext;
    }

    public void setListModel(final ListModel listModel) {
        mListModel = listModel;

        if (mListModel != null && mObserver != null) {
            mListModel.registerAdapterDataObserver(mObserver);
        }


    }

    @Override
    public void registerAdapterDataObserver(final RecyclerView.AdapterDataObserver observer) {
        if (observer == null) {
            return;
        }
        super.registerAdapterDataObserver(observer);

        mObserver = observer;

        if (mListModel != null) {
            mListModel.registerAdapterDataObserver(observer);
        }
        notifyDataSetChanged();
    }

    @Override
    public void unregisterAdapterDataObserver(final RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        mObserver = null;

        if (mListModel != null) {
            mListModel.unregisterAdapterDataObserver(observer);
        }
    }

    @Override
    public int getItemCount() {
        return mListModel.getItemCount();
    }

    public Object getItem(final int position) {

        if (position == mUndoPosition) {
            return UNDO_ITEM;
        } else {
            return mListModel.getItem(position);
        }
    }

    @Override
    public long getItemId(final int position) {
        return mListModel.getItemId(position);
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
        mListModel.updateViewHolder(viewHolder, position);
        viewHolder.setItemPosition(position);
        viewHolder.setListener(this);
    }

    public final int getUndoPosition() {
        return mUndoPosition;
    }

    public void remove(final int position) {

        int newUndoPosition = position;

        if (mUndoPosition >= 0) {
            commitRemove();

            if (mUndoPosition < newUndoPosition) {
                newUndoPosition--;
            }
        }

        mUndoPosition = newUndoPosition;
        notifyDataSetChanged();
    }

    public boolean canDismiss(final int position) {
        return mListModel.canDismiss(position);
    }

    public void undoRemove() {
        if (mUndoPosition >= 0) {
            mUndoPosition = -1;
            notifyDataSetChanged();
        }
    }

    public void commitRemove() {
        if (mUndoPosition >= 0) {
            mListModel.remove(mUndoPosition);
            mUndoPosition = -1;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        if (mUndoPosition >= 0) {
            commitRemove();
        }
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        if (mUndoPosition >= 0) {
            commitRemove();
        }
    }

    public static class UndoItem {

        private UndoItem() {
        }
    }
}

