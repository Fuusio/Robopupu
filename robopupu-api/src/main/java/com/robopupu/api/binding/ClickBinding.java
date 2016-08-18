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
import com.robopupu.api.mvp.ViewCompatDialogFragment;
import com.robopupu.api.mvp.ViewCompatFragment;

/**
 * {@link ClickBinding} can be establishing bindings from
 * UI widgets ({@link View} instances) to other objects, such as {@link Presenter}s.
 */
public class ClickBinding implements View.OnClickListener {

    private View[] views;

    private ViewBinding.ClickListener clickListener;

    private ClickBinding() {
    }

    public ClickBinding(final ViewCompatFragment fragment, final View... views) {
        final int count = views.length;
        this.views = new View[count];

        for (int i = 0; i < count; i++) {
            this.views[i] = views[i];
            attachListeners(this.views[i]);
        }
    }

    public ClickBinding(final ViewCompatDialogFragment dialogFragment, final View... views) {
        final int count = views.length;
        this.views = new View[count];

        for (int i = 0; i < count; i++) {
            this.views[i] = views[i];
            attachListeners(this.views[i]);
        }
    }

    public ClickBinding(final ViewCompatFragment fragment, @IdRes final int... viewIds) {
        final int count = viewIds.length;
        views = new  View[count];

        for (int i = 0; i < count; i++) {
            views[i] = fragment.getView(viewIds[i]);
            attachListeners(views[i]);
        }
    }

    public ClickBinding(final ViewCompatDialogFragment dialogFragment, @IdRes final int... viewIds) {
        final int count = viewIds.length;
        views = new  View[count];

        for (int i = 0; i < count; i++) {
            views[i] = dialogFragment.getView(viewIds[i]);
            attachListeners(views[i]);
        }
    }

    public ViewBinding.ClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(final ViewBinding.ClickListener listener) {
        clickListener = listener;
    }

    public final void setViewsEnabled(final boolean enabled) {
        for (final View view : views) {
            view.setEnabled(enabled);
        }
    }

    /**
     * Gets the {@link View} bound to this {@link ClickBinding}.
     *
     * @return A {@link View}.
     */
    public final View[] getViews() {
        return views;
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
        if (clickListener != null) {
            clickListener.onClicked(view);
        }
        clicked();
    }

    /**
     * This method should be overridden for dispatching {@link View.OnClickListener} events.
     */
    protected void clicked() {
    }
}
