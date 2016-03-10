/*
 * Copyright (C) 2001 - 2016 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.binding;

import android.support.annotation.IdRes;
import android.view.View;

import com.robopupu.api.mvp.Presenter;
import com.robopupu.api.mvp.ViewDialogFragment;
import com.robopupu.api.mvp.ViewFragment;

/**
 * {@link ClickBinding} can be establishing bindings from
 * UI widgets ({@link View} instances) to other objects, such as {@link Presenter}s.
 */
public class ClickBinding implements View.OnClickListener {

    private View[] mViews;

    private ViewBinding.ClickListener mClickListener;

    private ClickBinding() {
    }

    public ClickBinding(final ViewFragment fragment, final View... views) {
        final int count = views.length;
        mViews = new View[count];

        for (int i = 0; i < count; i++) {
            mViews[i] = views[i];
            attachListeners(mViews[i]);
        }
    }

    public ClickBinding(final ViewDialogFragment dialogFragment, final View... views) {
        final int count = views.length;
        mViews = new View[count];

        for (int i = 0; i < count; i++) {
            mViews[i] = views[i];
            attachListeners(mViews[i]);
        }
    }

    public ClickBinding(final ViewFragment fragment, @IdRes final int... viewIds) {
        final int count = viewIds.length;
        mViews = new  View[count];

        for (int i = 0; i < count; i++) {
            mViews[i] = fragment.getView(viewIds[i]);
            attachListeners(mViews[i]);
        }
    }

    public ClickBinding(final ViewDialogFragment dialogFragment, @IdRes final int... viewIds) {
        final int count = viewIds.length;
        mViews = new  View[count];

        for (int i = 0; i < count; i++) {
            mViews[i] = dialogFragment.getView(viewIds[i]);
            attachListeners(mViews[i]);
        }
    }

    public ViewBinding.ClickListener getClickListener() {
        return mClickListener;
    }

    public void setClickListener(final ViewBinding.ClickListener listener) {
        mClickListener = listener;
    }

    public final void setViewsEnabled(final boolean enabled) {
        for (final View view : mViews) {
            view.setEnabled(enabled);
        }
    }

    /**
     * Gets the {@link View} bound to this {@link ClickBinding}.
     *
     * @return A {@link View}.
     */
    public final View[] getViews() {
        return mViews;
    }

    /**
     * Invoked to attach the listeners to the given {@link View}. Methods overriding this method
     * has to call {@code super.attachListener(view)}.
     *
     * @param view A {@link View}.
     */
    protected void attachListeners(final View view) {
        view.setOnClickListener(this);
    }

    /**
     * Invoked to detach the listeners from the given {@link View}. Methods overriding this method
     * has to call {@code super.detachListeners(view)}.
     *
     * @param view A {@link View}.
     */
    protected void detachListeners(final View view) {
        view.setOnClickListener(null);
    }

    @Override
    public final void onClick(final View view) {
        if (mClickListener != null) {
            mClickListener.onClicked(view);
        }
        clicked();
    }

    /**
     * This method should be overridden for dispatching {@link View.OnClickListener} events.
     */
    protected void clicked() {
    }
}
