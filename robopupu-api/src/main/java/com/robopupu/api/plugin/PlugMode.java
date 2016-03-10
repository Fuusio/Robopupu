package com.robopupu.api.plugin;

/**
 * {@link PlugMode} defines an enum type for specifying various {@link Plug} modes.
 */
public enum PlugMode {
    REFERENCE,
    BROADCAST;

    public boolean isBroadcast() {
        return this == BROADCAST;
    }

    public boolean isReference() {
        return this == REFERENCE;
    }
}
