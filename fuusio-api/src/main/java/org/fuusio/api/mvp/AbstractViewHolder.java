/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://fuusio.org
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
package org.fuusio.api.mvp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

/**
 * {@link AbstractViewHolder} provides an abstract base class for implementing {@link ViewHolder}s.
 */
public class AbstractViewHolder extends RecyclerView.ViewHolder
        implements ViewHolder, View.OnClickListener {

    private final int mType;

    private View mInflatedView;
    private ViewHolderListener mItemListener;
    private int mItemPosition;

    protected AbstractViewHolder(final Context context, final int laytouResId, final int type) {
        super(inflateView(context, laytouResId));

        mType = type;

        mInflatedView = itemView;
        mInflatedView.setTag(this);
        mInflatedView.setOnClickListener(this);
    }

    public final View getInflatedView() {
        return mInflatedView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends View> T getView(final int resId) {
        return (T) mInflatedView.findViewById(resId);
    }

    private static View inflateView(final Context context, final int laytouResId) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View inflatedView = inflater.inflate(laytouResId, null, false);
        return inflatedView;
    }

    public ViewHolderListener getItemListener() {
        return mItemListener;
    }

    public void setListener(final ViewHolderListener listener) {
        mItemListener = listener;
    }

    public int getItemPosition() {
        return mItemPosition;
    }

    public void setItemPosition(int position) {
        mItemPosition = position;
    }

    public int getType() {
        return mType;
    }

    protected void notifyOnClicked() {
        if (mItemListener != null) {
            mItemListener.onViewClicked(this, mItemPosition);
        }
    }

    @Override
    public void onClick(final View view) {
        if (view == getInflatedView()) {
            notifyOnClicked();
        }
    }
}

