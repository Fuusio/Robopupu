package com.robopupu.api.mvp;

import com.robopupu.api.plugin.PlugInvoker;

/**
 * {@link ViewPlugInvoker} extends {@link PlugInvoker} to implement
 * {@link PlugInvoker}s specific for {@link View} interface.
 */
public abstract class ViewPlugInvoker<T extends View>
        extends PlugInvoker<T>  implements View {

    private String viewTag;

    @Override
    public ViewState getState() {
        if (plugins.size() > 0) {
            final View view = (View) plugins.get(0);
            return view.getState();
        }
        return new ViewState(null);
    }

    @Override
    public String getViewTag() {
        if (plugins.size() > 0) {
            final View view = (View) plugins.get(0);
            viewTag = view.getViewTag();
        }
        return viewTag;
    }

}
