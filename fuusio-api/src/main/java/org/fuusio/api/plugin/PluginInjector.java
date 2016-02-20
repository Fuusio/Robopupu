package org.fuusio.api.plugin;

import org.fuusio.api.dependency.DependencyScope;

/**
 * {@link PluginInjector} ... TODO
 */
public interface PluginInjector {

    DependencyScope getScope();

    void setScope(DependencyScope scope);

    <T> T plug(Class<T> pluginClass);

}
