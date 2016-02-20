/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://fuusio.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fuusio.api.binding;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterViewBinding<T_Item> extends ViewBinding<AdapterView>
        implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private Adapter mAdapter;

    public AdapterViewBinding(final Adapter<T_Item> adapter) {
        setAdapter(adapter);
    }

    public AdapterViewBinding(final Adapter<T_Item> adapter, final AdapterView view) {
        super(view);
        setAdapter(adapter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void setView(final AdapterView view) {
        super.setView(view);

        if (mAdapter != null) {
            mView.setAdapter(mAdapter);
            mAdapter.setView(mView);
        }
    }

    /**
     * Set the items to {@link Adapter}.
     *
     * @param items A {@link List} containing the items.
     */
    @SuppressWarnings("unchecked")
    public final void setItems(final List<T_Item> items) {
        mAdapter.setItems(items);
    }

    /**
     * Sets the {@link Adapter}.
     *
     * @param adapter An {@link Adapter}.
     */
    @SuppressWarnings("unchecked")
    public final void setAdapter(final Adapter<?> adapter) {
        mAdapter = adapter;

        if (mAdapter != null && mView != null) {
            mAdapter.setView(mView);
            mView.setAdapter(mAdapter);
        }
    }

    /**
     * Gets the currently selected item.
     *
     * @return The selected item. May be {@code null}.
     */
    @SuppressWarnings("unchecked")
    public final T_Item getSelectedItem() {
        final int index = mView.getSelectedItemPosition();

        if (index >= 0) {
            return (T_Item) mAdapter.getItem(index);
        }
        return null;
    }

    @Override
    public final boolean canBind(final View view) {
        return (view instanceof AdapterView);
    }

    @Override
    protected final void attachListeners(final AdapterView view) {
        super.attachListeners(view);
        view.setOnItemSelectedListener(this);
        view.setOnItemClickListener(this);
    }

    @Override
    protected final void detachListeners(final AdapterView view) {
        super.detachListeners(view);
        view.setOnItemSelectedListener(null);
        view.setOnItemClickListener(null);
    }

    /**
     * {@link Adapter} provides an abstract base class for implementing adapter for
     * {@link AdapterViewBinding}s bound to {@link AdapterView}s.
     *
     * @param <T_Item> The type of the items.
     */
    public static abstract class Adapter<T_Item> extends BaseAdapter {

        private final List<T_Item> mItems;

        private AdapterView mView;

        protected Adapter() {
            mItems = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(final int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }


        public void setItems(final List<T_Item> items) {
            mItems.clear();
            mItems.addAll(items);

            mView.post(new Runnable() {

                @Override
                public void run() {
                    Adapter.this.notifyDataSetChanged();
                    Adapter.this.notifyDataSetInvalidated();
                }
            });

        }

        @Override
        public abstract View getView(final int position, final View convertView, final ViewGroup parent);

        public void setView(final AdapterView view) {
            mView = view;
        }

    }

    @Override
    public final void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
        final Object selectedItem = parent.getItemAtPosition(position);
        itemSelected(selectedItem);
        itemSelected(parent, view, position, id);
    }

    @Override
    public final void onNothingSelected(final AdapterView<?> parent) {
        nothingSelected();
    }

    /**
     * This method should be overridden for dispatching {@link android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)} events.
     */
    protected void itemSelected(final Object item) {
        // By default do nothing
    }

    /**
     * This method should be overridden for dispatching {@link android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)} events.
     */
    protected void itemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
        // By default do nothing
    }

    /**
     * This method should be overridden for dispatching {@link android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android.widget.AdapterView)} events.
     */
    protected void nothingSelected() {
        // By default do nothing
    }

    @Override
    public final void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        final Object clickedItem = parent.getItemAtPosition(position);
        itemClicked(clickedItem);
        itemClicked(parent, view, position, id);
    }

    /**
     * This method should be overridden for dispatching {@link android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)} events.
     */
    protected void itemClicked(final Object item) {
        // By default do nothing
    }

    /**
     * This method should be overridden for dispatching {@link android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)} events.
     */
    protected void itemClicked(final AdapterView<?> parent, final View view, final int position, final long id) {
        // By default do nothing
    }

}
