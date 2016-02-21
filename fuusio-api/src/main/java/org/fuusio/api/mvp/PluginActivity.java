package org.fuusio.api.mvp;

import android.content.Context;
import android.os.Bundle;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.feature.FeatureContainer;
import org.fuusio.api.plugin.PluginBus;
import org.fuusio.api.plugin.PluginComponent;
import org.fuusio.api.plugin.PluginInjector;

/**
 * {@link PluginActivity} extends {@link ViewActivity} to provide an abstract base class for
 * implementing {@code Activities} that utilise Fuusio Plugin library.
 */
public abstract class PluginActivity<T_Presenter extends Presenter>
        extends ViewActivity<T_Presenter> implements PluginComponent {

    @Override
    protected void onStart() {
        PluginBus.plug(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        PluginBus.unplug(this);
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        // Do nothing by default
    }

    @Override
    public void onUnplugged(final PluginBus bus) {
        // Do nothing by default
    }

    @Override
    public void onPluginPlugged(final Object plugin) {
        // Do nothing by default
    }

    @Override
    public void onPluginUnplugged(final Object plugin) {
        // Do nothing by default
    }
}
