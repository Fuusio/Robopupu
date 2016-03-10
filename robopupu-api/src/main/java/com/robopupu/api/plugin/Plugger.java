package com.robopupu.api.plugin;

/**
 * {@link Plugger} defines interface for objects that are used to plug plugins to a {@link PluginBus}
 * and to unplug them from the {@link PluginBus}.
 */
public interface Plugger {

    void plug(Object plugin, PluginBus bus, boolean useHandler);
    void unplug(Object plugin, PluginBus bus);
}
