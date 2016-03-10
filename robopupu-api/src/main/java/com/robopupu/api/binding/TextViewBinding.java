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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

public class TextViewBinding extends ViewBinding<TextView> implements TextWatcher {

    private boolean mValidValue;

    public TextViewBinding(final TextView view) {
        super(view);
        mValidValue = true;
    }

    protected TextViewBinding() {
        this(null);
    }

    /**
     * Gets the text of the bound {@link TextView}.
     * @return The text as a {@link String}.
     */
    public String getText() {
        return mView.getText().toString();
    }

    /**
     * Set text of the bound {@link TextView}.
     *
     * @param text A {@link String} containing the text.
     */
    public void setText(final String text) {
        mView.setText(text);
    }

    @Override
    public final boolean canBind(final View view) {
        return (view instanceof TextView);
    }

    @Override
    protected final void attachListeners(final TextView view) {
        super.attachListeners(view);
        view.addTextChangedListener(this);
    }

    @Override
    protected final void detachListeners(final TextView view) {
        super.detachListeners(view);
        view.removeTextChangedListener(this);
    }

    @Override
    public final void beforeTextChanged(final CharSequence sequence, final int start, final int count, final int after) {
        textChanging(sequence.toString(), start, count, after);
    }

    @Override
    public final void onTextChanged(final CharSequence sequence, final int start, final int before, final int count) {
        mErrorMessage.clear();

        mValidValue = isValidValue(sequence.toString());

        if (mValidValue) {
            textChanged(sequence.toString(), start, before, count);
        } else {
            invalidTextChanged(sequence.toString(), start, before, count);
        }
    }

    @Override
    public final void afterTextChanged(final Editable editable) {

        final String text = editable.toString();
        mValidValue = isValidValue(text);

        if (mValidValue) {

            setValue(text);

            editableCommitted(editable);
            textCommitted(editable.toString());
        } else {
            invalidTextCommitted(editable.toString());
        }
    }

    /**
     * This method should be overridden for delegating {@link TextWatcher#afterTextChanged(Editable)}
     * events with {@link Editable} parameter.
     */
    protected void editableCommitted(final Editable editable) {
    }

    /**
     * This method should be overridden for delegating {@link TextWatcher#afterTextChanged(Editable)}
     * events with {@link String} parameter.
     */
    protected void textCommitted(final String text) {
    }

    /**
     * This method should be overridden for delegating {@link TextWatcher#afterTextChanged(Editable)}
     * events with {@link String} parameter.
     */
    protected void invalidTextCommitted(final String text) {
    }

    /**
     * This method should be overridden for delegating {@link TextWatcher#beforeTextChanged(CharSequence, int, int, int)} events.
     */
    protected void textChanging(final String text, final int start, final int count, final int after) {
    }

    /**
     * This method should be overridden for delegating {@link TextWatcher#onTextChanged(CharSequence, int, int, int)} events.
     */
    protected void textChanged(final CharSequence sequence, final int start, final int before, final int count) {
    }

    /**
     * This method should be overridden for delegating {@link TextWatcher#onTextChanged(CharSequence, int, int, int)} events.
     */
    protected void invalidTextChanged(final CharSequence sequence, final int start, final int before, final int count) {
    }

    /**
     * Sets the given value via this {@link TextViewBinding} to target.
     *
     * @param text A {@link String} representing the value.
     * @return The value as an {@link Object}. By default this value is {@code null}.
     */
    protected Object setValue(final String text) {
        // Do nothing by default
        return null;
    }

    /**
     * Tests if the given text represents a valid input value for the assigned {@link View}.
     *
     * @param text The input value given as a {@link String}.
     * @return A {@code boolean} value.
     */
    protected boolean isValidValue(final String text) {
        return true;
    }
}
