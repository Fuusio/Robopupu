package com.robopupu.api.graph.nodes;

import android.view.View;

import com.robopupu.api.graph.AbstractNode;
import com.robopupu.api.graph.AbstractOutputNode;

public class ViewNode extends AbstractOutputNode<View> implements View.OnClickListener {

    protected boolean mEnabled;
    protected View mView;

    public ViewNode(final View view) {
        mView = view;
        mView.setOnClickListener(this);
        mEnabled = true;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(final boolean enabled) {
        mEnabled = enabled;
    }

    public View getView() {
        return mView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onClick(final View view) {
        if (mEnabled) {
            out(view);
        }
    }
}
