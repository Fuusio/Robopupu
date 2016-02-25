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

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * {@link Dependency} implements a manager that is used to manage {@link DependencyScope}
 * instances. It provides a static API with methods for activating and deactivating
 * {@link DependencyScope} instances.
 */
public class Dependency {

    /**
     * A {@link DependencyScope} that has the same lifecycle as the application.
     */
    private static DependencyScope sAppScope;

    /**
     * A {@link HashMap} containing the instantiated {@link DependencyScope}s.
     */
    private final static HashMap<String, DependencyScope> sDependencyScopes = new HashMap<>();

    /**
     * The currently active {@link DependencyScope}.
     */
    private static DependencyScope sActiveScope = null;

    /**
     * Adds the {@link DependencyScope} owner by the given {@link DependencyScopeOwner} to
     * the {@link HashMap} of the current {@link DependencyScope}s.
     *
     * @param owner A {@link DependencyScopeOwner}
     * @return A {@link DependencyScope}.
     */
    public static DependencyScope addScope(final DependencyScopeOwner owner) {
        final DependencyScope scope = owner.getOwnedScope();
        sDependencyScopes.put(scope.getId(), scope);
        scope.addDependant(owner);
        return scope;
    }

    /**
     * Gets a {@link DependencyScope} managed by the given {@link DependencyScopeOwner}.
     *
     * @param owner A {@link DependencyScopeOwner}.
     * @param <T>   A type parameter for casting the requested dependency to expected type.
     * @return A {@link DependencyScope}. May return {@code null}.
     */
    @SuppressWarnings("unchecked")
    public static <T extends DependencyScope> T getScope(final DependencyScopeOwner owner) {
        return (T) sDependencyScopes.get(owner.getScopeClass().getCanonicalName());
    }

    /**
     * Gets a {@link DependencyScope} specified by the given canonical class name.
     *
     * @param scopeClass The canonical name of the {@link DependencyScope}.
     * @param <T>     A type parameter for casting the requested dependency to expected type.
     * @return A {@link DependencyScope}. May return {@code null}.
     */
    @SuppressWarnings("unchecked")
    public static <T extends DependencyScope> T getScope(final String scopeClass) {
        return (T) sDependencyScopes.get(scopeClass);
    }

    /**
     * Gets a specified {@link DependencyScope}.
     *
     * @param scopeClass A {@link Class} specifying {@link DependencyScope}.
     * @return The requested {@link DependencyScope}. If {@code null} is returned, it indicates an
     * error in an {@link DependencyScope} implementation.
     */
    @SuppressWarnings("unchecked")
    public static <T extends DependencyScope> T getScope(final Class<? extends DependencyScope> scopeClass) {

        if (sAppScope.getClass().isAssignableFrom(scopeClass)) {
            return (T) sAppScope;
        }

        final String id = scopeClass.getCanonicalName();
        DependencyScope scope  = sDependencyScopes.get(id);

        if (scope == null) {
            try {
                scope = scopeClass.newInstance();
                sDependencyScopes.put(id, scope);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to instantiate scope: " + id);
            }
        }
        return (T)scope;
    }

    /**
     * Gets the application level {@link DependencyScope}.
     * @return A {@link DependencyScope}.
     */
    public static DependencyScope getAppScope() {
        return sAppScope;
    }

    /**
     * Gets the {@link DependencyScope} that is set to be currently the active one. Note that only
     * one {@link DependencyScope} can be active at any given. If no {@link DependencyScope} is
     * set to be active, the application level {@link DependencyScope} is returned.
     *
     * @return A {@link DependencyScope}. May not return {@code null}.
     */
    @NonNull
    public static DependencyScope getActiveScope() {
        if (sActiveScope != null) {
            return sActiveScope;
        }
        return sAppScope;
    }

    /**
     * Sets the application level {@link DependencyScope}.
     * @param appScope A {@link DependencyScope}.
     */
    public static void setAppScope(final DependencyScope appScope) {
        if (appScope ==null) {
            throw new IllegalArgumentException("Parameter scope may not be null");
        }
        sAppScope = appScope;
    }

    /**
     * Activates a {@link DependencyScope} for the given {@link DependencyScopeOwner}.
     * The active {@link DependencyScope} is used for resolving dependencies. Setting the currently
     * active {@link DependencyScope} is not thread safe. Therefore this method can be invoked
     * only from the Main UI thread.
     *
     * @param owner A {@link DependencyScopeOwner}.
     */
    public static void activateScope(final DependencyScopeOwner owner) {
        final String id = owner.getScopeClass().getCanonicalName();
        DependencyScope scope = sDependencyScopes.get(id);

        if (scope == null) {
            scope = addScope(owner);
            scope.setOwner(owner);
        }

        if (sActiveScope != scope) {

            if (sActiveScope != null) {
                deactivateScope(sActiveScope.getOwner());
            }
            sActiveScope = scope;
            sActiveScope.initialize();
            sActiveScope.onActivated(owner);
        }
    }


    /**
     * Activates the given {@link DependencyScope} for the given {@link DependencyScopeOwner}.
     * The active {@link DependencyScope} is used for resolving dependencies. Setting the currently
     * active {@link DependencyScope} is not thread safe. Therefore this method can be invoked
     * only from the Main UI thread. This method is suitable to be used when the actual instance
     *
     * @param owner A {@link DependencyScopeOwner}.
     * @param scope A {@link DependencyScope}.
     */
    public static void activateScope(final DependencyScopeOwner owner, final DependencyScope scope) {

        if (scope ==null) {
            throw new IllegalArgumentException("Parameter scope may not be null");
        }

        final String id = owner.getScopeClass().getCanonicalName();
        sDependencyScopes.put(id, scope);
        scope.addDependant(owner);
        scope.setOwner(owner);

        if (sActiveScope != scope) {

            if (sActiveScope != null) {
                deactivateScope(sActiveScope.getOwner());
            }
            sActiveScope = scope;
            sActiveScope.initialize();
            sActiveScope.onActivated(owner);
        }
    }

    /**
     * Deactivates a {@link DependencyScope} managed by the given {@link DependencyScopeOwner}.
     * When a {@link DependencyScope} is deactivated it is also disposed if disposing is allowed
     * for the deactivated {@link DependencyScope}.
     *
     * @param owner A {@link DependencyScopeOwner}.
     */
    public static void deactivateScope(final DependencyScopeOwner owner) {

        final DependencyScope scope = owner.getOwnedScope();

        if (scope.isDisposable()) {
            sDependencyScopes.remove(scope.getId());
            scope.dispose();
        }

        scope.onDeactivated(owner);

        if (scope == sActiveScope) {
            sActiveScope = null;
        }
    }

    /**
     * Disposes the {@link DependencyScope} of the given {@link DependencyScopeOwner}.
     *
     * @param owner A {@link DependencyScopeOwner}.
     */
    public static void disposeScope(final DependencyScopeOwner owner) {
        final DependencyScope scope = owner.getOwnedScope();

        if (scope.isDisposable()) {
            sDependencyScopes.remove(scope.getId());
            scope.dispose();
        }

        scope.onDeactivated(owner);

        if (scope == sActiveScope) {
            sActiveScope = null;
        }
    }

    /**
     * Gets a requested dependency of the specified type. The dependency is requested from
     * the currently active {@link DependencyScope}.
     *
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T get(final Class<T> dependencyType) {
        return getActiveScope().getDependency(dependencyType, null, false);
    }

    /**
     * Gets a requested dependency of the specified type. The dependency is requested from
     * the given {@link DependencyScope}.
     *
     * @param scopeType A {@link Class} specifying {@link DependencyScope}.
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T get(final Class<? extends DependencyScope> scopeType, final Class<T> dependencyType) {
        return get(scopeType, dependencyType, null);
    }

    /**
     * Gets a specified {@link DependencyScope}.
     *
     * @param scopeClass A {@link Class} specifying {@link DependencyScope}.
     * @return The requested {@link DependencyScope}. If {@code null} is returned, it indicates an
     * error in an {@link DependencyScope} implementation.
     */
    public static DependencyScope getDependencyScope(final Class<? extends DependencyScope> scopeClass) {
        DependencyScope scope = sDependencyScopes.get(scopeClass.getCanonicalName());

        if (scope == null) {
            try {
                scope = scopeClass.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException("Failed to instantiate scope: " + scopeClass.getCanonicalName());
            }
        }
        return scope;
    }

    /**
     * Gets a requested dependency of the specified type. The dependency is requested from
     * the specified {@link DependencyScope}.
     *
     * @param scopeClass A {@link Class} specifying {@link DependencyScope}.
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param dependant      The object requesting the requested. This parameter is required when the requesting object
     *                       is also a requested within the object graph represented by the active {@link Dependency}.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T get(final Class<? extends DependencyScope> scopeClass, final Class<T> dependencyType, final Object dependant) {
        DependencyScope scope = getDependencyScope(scopeClass);

        if (scope != null) {
            return scope.getDependency(dependencyType, dependant, false);
        }
        return null;
    }

    /**
     * Gets a requested dependency of the specified type. The dependency is requested from
     * the given {@link DependencyScope}.
     *
     * @param scope A {@link DependencyScope}.
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T get(final DependencyScope scope, final Class<T> dependencyType) {
        return scope.getDependency(dependencyType, null, false);
    }

    /**
     * Gets a requested dependency of the specified type. The dependency is requested from
     * the currently active {@link DependencyScope}.
     *
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param dependant      The object requesting the requested. This parameter is required when the requesting object
     *                       is also a requested within the object graph represented by the active {@link Dependency}.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T get(final Class<T> dependencyType, final Object dependant) {
        return getActiveScope().getDependency(dependencyType, dependant, false);
    }

    /**
     * Gets a requested dependency of the specified type. The dependency is requested from
     * the currently active {@link DependencyScope}.
     *
     * @param scope A {@link DependencyScope}.
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param dependant      The object requesting the requested. This parameter is required when the requesting object
     *                       is also a requested within the object graph represented by the active {@link Dependency}.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T get(final DependencyScope scope, final Class<T> dependencyType, final Object dependant) {
        return scope.getDependency(dependencyType, dependant, false);
    }

    /**
     * Gets a requested dependency of the specified type. The dependency is requested from
     * the currently active {@link DependencyScope}. If no requested dependency instance exists,
     * a new instance is created.
     *
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T getOrCreate(final Class<T> dependencyType) {
        return getActiveScope().getDependency(dependencyType, null, true);
    }

    /**
     * Gets a requested dependency of the specified type. The dependency is requested from
     * the given {@link DependencyScope}. If no requested dependency instance exists,
     * a new instance is created.
     *
     * @param scope A {@link DependencyScope}.
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T getOrCreate(final DependencyScope scope, final Class<T> dependencyType) {
        return scope.getDependency(dependencyType, null, true);
    }

    /**
     * Gets a requested dependency of the specified type. The dependency is requested from
     * the currently active {@link DependencyScope}. If no requested dependency instance exists,
     * a new instance is created.
     *
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param dependant      The object requesting the requested. This parameter is required when the requesting object
     *                       is also a requested within the object graph represented by the active {@link Dependency}.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T getOrCreate(final Class<T> dependencyType, final Object dependant) {
        return getActiveScope().getDependency(dependencyType, dependant, true);
    }

    /**
     * Creates a new instance of the requested dependency type. The currently active
     * {@link DependencyScope} is requested to create the instance.
     *
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T create(final Class<T> dependencyType) {
        return getActiveScope().createDependency(dependencyType, null);
    }

    /**
     * Creates a new instance of the requested dependency type. The currently active
     * {@link DependencyScope} is requested to create the instance.
     *
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param dependant      The object requesting the requested. This parameter is required when the requesting object
     *                       is also a requested within the object graph represented by the active {@link Dependency}.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T create(final Class<T> dependencyType, final Object dependant) {
        return getActiveScope().createDependency(dependencyType, dependant);
    }
}
