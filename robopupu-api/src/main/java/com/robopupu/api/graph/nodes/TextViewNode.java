package com.robopupu.api.graph.nodes;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.robopupu.api.graph.Node;

public class TextViewNode extends Node<TextView, String> implements TextWatcher {

    protected boolean mEnabled;
    private TextView mTextView;

    public TextViewNode(final TextView textView) {
        mTextView = textView;
        mTextView.addTextChangedListener(this);
        mEnabled = true;
    }

    @SuppressWarnings("unchecked")
    public <T extends TextView>  T getView() {
        return (T)mTextView;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(final boolean enabled) {
        mEnabled = enabled;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        // Do nothing
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        // Do nothing
    }

    @Override
    public void afterTextChanged(final Editable editable) {
        if (mEnabled) {
            emitOutput(editable.toString());
        }
    }
}
