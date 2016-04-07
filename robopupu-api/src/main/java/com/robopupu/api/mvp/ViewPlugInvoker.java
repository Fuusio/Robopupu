package com.robopupu.api.mvp;

import com.robopupu.api.plugin.PlugInvoker;

/**
 * {@link ViewPlugInvoker} extends {@link PlugInvoker} to implement
 * {@link PlugInvoker}s specific for {@link View} interface.
 */
public abstract class ViewPlugInvoker<T extends View>
        extends PlugInvoker<T>  implements View {

    private String mViewTag;

    @Override
    public ViewState getState() {
        if (mPlugins.size() > 0) {
            final View view = (View) mPlugins.get(0);
            return view.getState();
        }
        return new ViewState(null);
    }

    @Override
    public String getViewTag() {
        if (mPlugins.size() > 0) {
            final View view = (View) mPlugins.get(0);
            mViewTag = view.getViewTag();
        }
        return mViewTag;
    }

}
