package com.robopupu.api.mvp;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

/**
 * {@link ViewEventsDelegate} implements selected event listener interfaces of {@link View}-based widgets.
 * It delegates the event invocations received via those interfaces to a assigned
 * {@link PresenterDelegate}.
 */
public class ViewEventsDelegate
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextWatcher {

    private PresenterDelegate delegate;
    private View view;

    public ViewEventsDelegate(final View view, final PresenterDelegate delegate) {
        this.view = view;
        this.delegate = delegate;

        final String tag = ((String)view.getTag()).toLowerCase();
        this.view.setTag(tag);
    }

    @Override
    public void onCheckedChanged(final CompoundButton button, final boolean checked) {
        delegate.onChecked((String) button.getTag(), checked);
    }

    @Override
    public void onClick(final View view) {
        delegate.onClick((String) view.getTag());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Do nothing
    }

    @Override
    public void afterTextChanged(final Editable editable) {
        delegate.onTextChanged((String) view.getTag(), editable.toString());
    }
}
