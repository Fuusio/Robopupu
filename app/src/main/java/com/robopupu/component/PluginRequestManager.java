package com.robopupu.component;

import org.fuusio.api.network.Request;
import org.fuusio.api.network.RequestManager;
import org.fuusio.api.plugin.PlugInterface;

/**
 * {@link PluginRequestManager} defines a plugin interface for component that implements
 * the {@link RequestManager} interface for executing {@link Request}s,
 */
@PlugInterface
public interface PluginRequestManager extends RequestManager {
}
