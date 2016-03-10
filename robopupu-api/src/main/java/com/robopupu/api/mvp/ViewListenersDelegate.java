package com.robopupu.api.mvp;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

/**
 * {@link ViewListenersDelegate} implements various event listener interfaces from Android
 * {@link View} and widgets. It delegate event invocation received via those interfaces to
 * a wrapped {@link PresenterDelegate}.
 */
public class ViewListenersDelegate
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextWatcher {

    private PresenterDelegate mDelegate;
    private View mView;

    public ViewListenersDelegate(final View view, final PresenterDelegate delegate) {
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
