package com.robopupu.component;

import org.fuusio.api.plugin.PlugInterface;
import org.fuusio.api.plugin.PlugMode;

@PlugInterface(PlugMode.BROADCAST)
public interface ExitObserver {

    void onExit();
}
