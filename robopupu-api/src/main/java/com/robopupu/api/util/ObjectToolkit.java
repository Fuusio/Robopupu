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

public final class ObjectToolkit {

    private ObjectToolkit() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(final Object object) {
        return (Class<T>) object.getClass();
    }

    public static String getHumanReadableClassName(final Object object) {
        final Class<Object> objectClass = getClass(object);
        String className = objectClass.getSimpleName();

        if (objectClass.isMemberClass()) {
            className = objectClass.getDeclaringClass().getSimpleName() + "." + className;
        }
        return className;
    }
}
