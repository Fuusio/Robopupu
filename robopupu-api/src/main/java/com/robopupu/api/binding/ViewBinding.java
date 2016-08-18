/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
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

import android.view.View;

import com.robopupu.api.mvp.Presenter;
import com.robopupu.api.util.MessageContext;

/**
 * {@link ViewBinding} provides an abstract base class for objects that establish bindings from
 * UI widgets ({@link View} instances) to other objects, such as {@link Presenter}s.
 */
public abstract class ViewBinding<T_View extends View> implements View.OnClickListener {

    protected ClickListener clickListener;
    protected MessageContext errorMessage;
    protected T_View view;

    protected ViewBinding() {
    }

    protected ViewBinding(final T_View view) {
        if (view != null) {
            setView(view);
        } else {
            this.view = null;
        }
    }

    public ClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ClickListener listener) {
        clickListener = listener;
    }

    /**
     * Tests if the attached {@link View} is enabled.
     *
     * @return A {@link boolean}.
     */
    public final boolean isViewEnabled() {
        return view.isEnabled();
    }

    public final void setViewEnabled(final boolean enabled) {
        view.setEnabled(enabled);
    }

    @SuppressWarnings("unchecked")
    public final <T> T getViewTag() {
        return (T) view.getTag();
    }

    @SuppressWarnings("unchecked")
    public final <T> T getViewTag(final int key) {
        return (T) view.getTag(key);
    }

    public void setViewTag(final Object tag) {
        view.setTag(tag);
    }

    public void setViewTag(final int key, final Object tag) {
        view.setTag(key, tag);
    }

    /**
     * Gets the {@link View} bound to this {@link ViewBinding}.
     *
     * @return A {@link View}.
     */
    public final T_View getView() {
        return view;
    }

    /**
     * Sets the {@link View} bound to this {@link ViewBinding}.
     *
     * @param view A {@link View}.
     */
    public void setView(final T_View view) {

        if (view != null) {
            this.view = view;
            errorMessage = new MessageContext(view.getContext());
            attachListeners(this.view);
        } else if (this.view != null) {
            detachListeners(this.view);
            this.view = null;
        }
    }

    public final int getViewVisibility() {
        return view.getVisibility();
    }

    public final void setViewVisibility(final int visibility) {
        view.setVisibility(visibility);
    }

    /**
     * Tests if the given {@link View} can be bound to this {@link ViewBinding}.
     *
     * @param view A  {@link View}.
     * @return A {@code boolean} value.
     */
    public abstract boolean canBind(final View view);

    /**
     * Invoked to attach the listeners to the given {@link View}. Methods overriding this method
     * has to call {@code super.attachListener(view)}.
     *
     * @param view A {@link View}.
     */
    protected void attachListeners(final T_View view) {
        view.setOnClickListener(this);
    }

    /**
     * Invoked to detach the listeners from the given {@link View}. Methods overriding this method
     * has to call {@code super.detachListeners(view)}.
     *
     * @param view A {@link View}.
     */
    protected void detachListeners(final T_View view) {
        view.setOnClickListener(null);
    }

    @Override
    public void onClick(final View view) {
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

    /**
     * Requests focus for thw bound {@link View}.
     */
    public void requestFocus() {
        view.requestFocus();
    }

    public void setErrorMessage(final String message, final Object... args) {
        errorMessage.setMessage(message);
        errorMessage.setMessageArgs(args);
    }

    public void setErrorMessage(final int messageResId, final Object... args) {
        errorMessage.setMessage(messageResId);
        errorMessage.setMessageArgs(args);
    }

    public void clearErrorMessage() {
        errorMessage.clear();
    }

    public interface ClickListener {

        void onClicked(View view);
    }
}
