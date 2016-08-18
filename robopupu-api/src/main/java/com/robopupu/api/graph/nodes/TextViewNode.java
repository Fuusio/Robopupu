package com.robopupu.api.graph.nodes;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.robopupu.api.graph.Node;

/**
 * {@link TextViewNode} emits the the text content of the assigmmed {@link TextView} the change of
 * the content is committed.
 */
public class TextViewNode extends Node<TextView, String> implements TextWatcher {

    protected boolean enabled;
    private TextView textView;

    public TextViewNode(final TextView textView) {
        this.textView = textView;
        this.textView.addTextChangedListener(this);
        enabled = true;
    }

    @SuppressWarnings("unchecked")
    public <T extends TextView>  T getView() {
        return (T) textView;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
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
        if (enabled) {
            emitOutput(editable.toString());
        }
    }
}
