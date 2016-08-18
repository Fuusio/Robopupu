/*
 * Copyright (C) 2001-2015 Marko Salmela.
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

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AbstractListenable} is an abstract base class for concrete implementations of
 * {@link Listenable} interface.
 */
public abstract class AbstractListenable<T_Listener> implements Listenable<T_Listener> {

    private final transient ArrayList<T_Listener> listeners;
    private final transient boolean singled;

    protected AbstractListenable() {
        listeners = new ArrayList<>();
        singled = getClass().isAnnotationPresent(Singled.class);
    }

    @Override
    public T_Listener getSingleListener() {
        if (singled) {
            if (listeners.size() == 1) {
                return listeners.get(0);
            } else {
                return null;
            }
        } else {
            throw new IllegalStateException("Use of getSingleListener() is allowed only with annotation Singled");
        }
    }

    @Override
    public synchronized final List<T_Listener> getListeners() {
        return new ArrayList<>(listeners);
    }

    @Override
    public synchronized int getListenerCount() {
        return listeners.size();
    }

    @Override
    public synchronized boolean addListener(final T_Listener listener) {

        if (!listeners.contains(listener)) {
            if (singled && !listeners.isEmpty()) {
                throw new IllegalStateException("This Listenable is annotated to allow only a single listener");
            }
            listeners.add(listener);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean removeListener(final T_Listener listener) {
        return listeners.remove(listener);
    }

    @Override
    public synchronized void removeAllListeners() {
        listeners.clear();
    }

    @Override
    public synchronized boolean hasAnyListeners() {
        return !listeners.isEmpty();
    }

    @Override
    public boolean isSingled() {
        return singled;
    }
}
