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

    private PresenterDelegate mDelegate;
    private View mView;

    public ViewEventsDelegate(final View view, final PresenterDelegate delegate) {
        mView = view;
        mDelegate = delegate;

        final String tag = ((String)view.getTag()).toLowerCase();
        mView.setTag(tag);
    }

    @Override
    public void onCheckedChanged(final CompoundButton button, final boolean checked) {
        mDelegate.onChecked((String) button.getTag(), checked);
    }

    @Override
    public void onClick(final View view) {
        mDelegate.onClick((String) view.getTag());
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
        mDelegate.onTextChanged((String) mView.getTag(), editable.toString());
    }
}
