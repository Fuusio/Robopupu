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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * {@link Listenable} defines an interface for objects that can have listeners for listening their
 * events. A concrete implementation of {@link Listenable} can be annotated using
 * {@link Singled} annotation to allow only one listener.
 */
public interface Listenable<T_Listener> {

    /**
     * Return a single listener if there is only one listener. Otherwise throws
     * a {@link IllegalStateException}
     * @return A listener.
     */
    T_Listener getSingleListener();

    List<T_Listener> getListeners();

    int getListenerCount();

    boolean addListener(T_Listener listener);

    boolean removeListener(T_Listener listener);

    void removeAllListeners();

    boolean hasAnyListeners();

    /**
     * Test if this {@link Listenable} is annotated with {@link Singled} to indicate that this
     * {@link Listenable} is allowed to have at most a single listener.
     * @return A {@code boolean} value.
     */
    boolean isSingled();

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Singled {
    }

}
