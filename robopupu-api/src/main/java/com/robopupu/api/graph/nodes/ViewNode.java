package com.robopupu.api.graph.nodes;

import android.view.View;

import com.robopupu.api.graph.Node;

/**
 * {@link ViewNode} emits the assigned {@link View} an output value when it is clicked.
 */
public class ViewNode extends Node<View, View> implements View.OnClickListener {

    protected boolean mEnabled;
    protected View mView;

    public ViewNode(final View view) {
        mView = view;
        mView.setOnClickListener(this);
        mEnabled = true;
    }

    @SuppressWarnings("unchecked")
    public <T extends View>  T getView() {
        return (T)mView;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(final boolean enabled) {
        mEnabled = enabled;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onClick(final View view) {
        if (mEnabled) {
            emitOutput(view);
        }
    }
}
