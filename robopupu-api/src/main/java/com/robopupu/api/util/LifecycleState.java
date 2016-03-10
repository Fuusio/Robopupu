/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robopupu.api.util;

public enum LifecycleState {

    DORMANT("Dormant"), //
    CREATED("Created"), //
    STARTED("Started"), //
    RESUMED("Resumed"), //
    PAUSED("Paused"), //
    STOPPED("Stopped"), //
    DESTROYED("Destroyed");

    private final String mLabel;

    LifecycleState(final String label) {
        mLabel = label;
    }

    public String getLabel() {
        return mLabel;
    }

    public final boolean isDormant() {
        return (this == DORMANT);
    }

    public final boolean isCreated() {
        return (this == CREATED);
    }

    public final boolean isStarted() {
        return (this == STARTED);
    }

    public final boolean isResumed() {
        return (this == RESUMED);
    }

    public final boolean isPaused() {
        return (this == PAUSED);
    }

    public final boolean isStopped() {
        return (this == STOPPED);
    }

    public final boolean isDestroyed() {
        return (this == DESTROYED);
    }
}
