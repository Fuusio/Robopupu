package com.robopupu.api.graph.nodes;

import android.view.View;

import com.robopupu.api.graph.Node;

/**
 * {@link ViewNode} emits the assigned {@link View} an output value when it is clicked.
 */
public class ViewNode extends Node<View, View> implements View.OnClickListener {

    protected boolean enabled;
    protected View view;

    public ViewNode(final View view) {
        this.view = view;
        this.view.setOnClickListener(this);
        enabled = true;
    }

    @SuppressWarnings("unchecked")
    public <T extends View>  T getView() {
        return (T) view;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onClick(final View view) {
        if (enabled) {
            emitOutput(view);
        }
    }
}
