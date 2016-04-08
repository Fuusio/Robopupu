package com.robopupu.api.component;

import com.robopupu.api.plugin.AbstractPluginStateComponent;
import com.robopupu.api.plugin.PluginComponent;

/**
 * {@link AbstractInteractor} provides an abstract base class for implementing
 * {@link Interactor}s.
 */
public abstract class AbstractInteractor extends AbstractPluginStateComponent implements Interactor {

    protected AbstractInteractor() {
    }
}
