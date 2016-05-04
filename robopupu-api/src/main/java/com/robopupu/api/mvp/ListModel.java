/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.mvp;

import android.support.v7.widget.RecyclerView;

public interface ListModel<T_ModelEvent extends ModelEvent, T_ModelListener extends ModelListener<T_ModelEvent>>
        extends Model<T_ModelEvent, T_ModelListener>  {

    int getItemCount();

    <T> T getItem(int position);

    long getItemId(int position);

    boolean hasStableIds();

    boolean isEmpty();

    void updateViewHolder(ViewHolder viewHolder, int position);

    void remove(int position);

    boolean canDismiss(int position);

    void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer);

    void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer);

    void notifyDataChanged();
}
