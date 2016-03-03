package com.robopupu.component;

import com.robopupu.app.RobopupuAppScope;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.network.volley.VolleyRequestManager;
import org.fuusio.api.plugin.Plugin;

/**
 * {@link PluginRequestManagerImpl} implements a {@link PluginRequestManager} as a plugin.
 */
@Plugin
public class PluginRequestManagerImpl extends VolleyRequestManager implements PluginRequestManager {

    @Scope(RobopupuAppScope.class)
    @Provides(PluginRequestManager.class)
    public PluginRequestManagerImpl() {
    }
}
