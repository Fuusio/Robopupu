/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://fuusio.org
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
package org.fuusio.api.dependency;

/**
 * An interface for objects that provide and own a {@link DependencyScope}s. Object implementing
 * this interface typically also manage the lifecycle of the {@link DependencyScope} they provide
 * by activating and deactivating it.
 */
public interface DependencyScopeOwner {

    /**
     * Gets the {@link DependencyScope} owned by this {@link DependencyScopeOwner}.
     *
     * @return A {@link DependencyScope}.
     */
    DependencyScope getScope();

    /**
     * Gets the {@link Class} of {@link DependencyScope} owned by this {@link DependencyScopeOwner}.
     *
     * @return A {@link Class}.
     */
    Class<? extends DependencyScope> getScopeClass();
}
