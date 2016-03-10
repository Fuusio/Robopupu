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

    private final transient ArrayList<T_Listener> mListeners;
    private final transient boolean mSingled;

    protected AbstractListenable() {
        mListeners = new ArrayList<>();
        mSingled = getClass().isAnnotationPresent(Singled.class);
    }

    @Override
    public T_Listener getSingleListener() {
        if (mSingled) {
            if (mListeners.size() == 1) {
                return mListeners.get(0);
            } else {
                return null;
            }
        } else {
            throw new IllegalStateException("Use of getSingleListener() is allowed only with annotation Singled");
        }
    }

    @Override
    public synchronized final List<T_Listener> getListeners() {
        return new ArrayList<>(mListeners);
    }

    @Override
    public synchronized int getListenerCount() {
        return mListeners.size();
    }

    @Override
    public synchronized boolean addListener(final T_Listener listener) {

        if (!mListeners.contains(listener)) {
            if (mSingled && !mListeners.isEmpty()) {
                throw new IllegalStateException("This Listenable is annotated to allow only a single listener");
            }
            mListeners.add(listener);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean removeListener(final T_Listener listener) {
        return mListeners.remove(listener);
    }

    @Override
    public synchronized void removeAllListeners() {
        mListeners.clear();
    }

    @Override
    public synchronized boolean hasAnyListeners() {
        return !mListeners.isEmpty();
    }

    @Override
    public boolean isSingled() {
        return mSingled;
    }
}
