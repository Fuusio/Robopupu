/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.dependency;

import android.support.annotation.NonNull;
import android.util.Log;

import com.robopupu.api.util.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * {@link Dependency} implements a manager that is used to manage {@link DependencyScope}
 * instances. It provides a static API with methods for activating and deactivating
 * {@link DependencyScope} instances.
 */
public class Dependency {

    private final static String TAG = Utils.tag(Dependency.class);

    /**
     * A {@link DependencyScope} that has the same lifecycle as the application.
     */
    private static DependencyScope appScope;

    /**
     * A {@link HashMap} containing the instantiated {@link DependencyScope}s.
     */
    private final static HashMap<String, DependencyScope> dependencyScopes = new HashMap<>();

    /**
     * The currently active {@link DependencyScope}.
     */
    private static DependencyScope activeScope = null;

    /**
     * Adds the {@link DependencyScope} owner by the given {@link DependencyScopeOwner} to
     * the {@link HashMap} of the current {@link DependencyScope}s.
     *
     * @param owner A {@link DependencyScopeOwner}
     * @return A {@link DependencyScope}.
     */
    public static DependencyScope addScope(final DependencyScopeOwner owner) {
        final DependencyScope scope = owner.getOwnedScope();
        dependencyScopes.put(scope.getId(), scope);
        scope.addDependant(owner);
        return scope;
    }

    /**
     * Gets a {@link DependencyScope} managed by the given {@link DependencyScopeOwner}.
     *
     * @param owner A {@link DependencyScopeOwner}.
     * @param <T>   A type parameter for casting the requested dependency to expected type.
     * @param createInstance A {@code boolean} flag specifying if the requested {@link DependencyScope} is
     *               attempted to be created via reflection in case the requested {@link DependencyScope}
     *                       is not registered yet.
     * @return A {@link DependencyScope}. May return {@code null}.
     */
    @SuppressWarnings("unchecked")
    public static <T extends DependencyScope> T getScope(final DependencyScopeOwner owner, final boolean createInstance) {
        T scope = (T)owner.getOwnedScope();

        if (scope == null) {
            scope = getScope(owner.getScopeClass(), createInstance);
        }
        return scope;
    }

    /**
     * Gets a {@link DependencyScope} specified by the given canonical class name.
     *
     * @param scopeClassName The canonical name of the {@link DependencyScope}.
     * @param createInstance A {@code boolean} flag specifying if the requested {@link DependencyScope} is
     *               attempted to be created via reflection in case the requested {@link DependencyScope}
     *                       is not registered yet.
     * @param <T>     A type parameter for casting the requested dependency to expected type.
     * @return A {@link DependencyScope}. May return {@code null}.
     */
    @SuppressWarnings("unchecked")
    public static <T extends DependencyScope> T getScope(final String scopeClassName, final boolean createInstance) {
        T scope = (T) dependencyScopes.get(scopeClassName);

        if (scope == null) {
            Class<? extends DependencyScope> scopeClass = null;

            try {
                scopeClass = (Class<? extends DependencyScope>)Class.forName(scopeClassName);
            } catch (ClassNotFoundException e) {
                Log.d(TAG, "Class: " + scopeClassName + " was not found.");
            }
            scope = getScope(scopeClass, createInstance);
        }
        return scope;
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
        return getScope(scopeClass, true);
    }

    /**
     * Gets a specified {@link DependencyScope}.
     *
     * @param scopeClass A {@link Class} specifying {@link DependencyScope}.
     * @param createInstance A {@code boolean} flag specifying if the requested {@link DependencyScope} is
     *               attempted to be created via reflection in case the requested {@link DependencyScope}
     *                       is not registered yet.
     * @return The requested {@link DependencyScope}. If {@code null} is returned, it indicates an
     * error in an {@link DependencyScope} implementation.
     */
    @SuppressWarnings("unchecked")
    public static <T extends DependencyScope> T getScope(final Class<? extends DependencyScope> scopeClass, final boolean createInstance) {

        if (appScope.getClass().isAssignableFrom(scopeClass)) {
            return (T) appScope;
        }

        final String id = scopeClass.getCanonicalName();
        DependencyScope scope  = dependencyScopes.get(id);

        if (scope == null && createInstance) {
            try {
                scope = scopeClass.newInstance();
                dependencyScopes.put(id, scope);
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
        return appScope;
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
        if (activeScope != null) {
            return activeScope;
        }
        return appScope;
    }

    /**
     * Sets the application level {@link DependencyScope}.
     * @param appScope A {@link DependencyScope}.
     */
    public static void setAppScope(final DependencyScope appScope) {
        if (appScope ==null) {
            throw new IllegalArgumentException("Parameter scope may not be null");
        }
        Dependency.appScope = appScope;
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
        DependencyScope scope = dependencyScopes.get(id);

        if (scope == null) {
            scope = addScope(owner);
        }

        scope.setOwner(owner);

        if (activeScope != scope && !scope.isAppScope()) {
            activeScope = scope;
            activeScope.initialize();
            activeScope.onActivated(owner);
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

        final Class<? extends DependencyScope> scopeClass = owner.getScopeClass();

        if (!scopeClass.isAssignableFrom(scope.getClass())) {
            final String message = "DependencyScopeOwner of type: " + owner.getClass().getName() +
                    " cannot own DependencyScope of type: " +
                    scope.getClass().getName() +
                    ". The expected type is: " + scopeClass.getName();
            throw new IllegalArgumentException(message);
        }

        final String id = scopeClass.getCanonicalName();
        dependencyScopes.put(id, scope);
        scope.addDependant(owner);
        activateScope(owner);
    }

    /**
     * Deactivates a {@link DependencyScope} managed by the given {@link DependencyScopeOwner}.
     * When a {@link DependencyScope} is deactivated it is also disposed if disposing is allowed
     * for the deactivated {@link DependencyScope}.
     *
     * @param owner A {@link DependencyScopeOwner}.
     */
    public static void deactivateScope(final DependencyScopeOwner owner) {
        disposeScope(owner);
    }

    /**
     * Disposes the {@link DependencyScope} of the given {@link DependencyScopeOwner}.
     *
     * @param owner A {@link DependencyScopeOwner}.
     */
    public static void disposeScope(final DependencyScopeOwner owner) {
        final DependencyScope scope = owner.getOwnedScope();

        if (scope != null && dependencyScopes.containsKey(scope.getId())) {
            if (scope.isDisposable()) {
                dependencyScopes.remove(scope.getId());
                scope.dispose();
            }

            scope.onDeactivated(owner);

            if (scope == activeScope) {
                activeScope = null;
            }
        }
    }

    /**
     * Invoked to dispose all the registered {@link DependencyScope}s.
     */
    public static void disposeScopes() {
        final List<DependencyScope> scopes = new ArrayList<>();
        scopes.addAll(dependencyScopes.values());

        for (final DependencyScope scope : scopes) {
            scope.dispose();
        }
        dependencyScopes.clear();
        resetActiveScope();
    }

    /**
     * Gets a dependency of the specified type. The dependency is requested from
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
     * Gets a dependency of the specified type. The dependency is requested from
     * the given {@link DependencyScope}.
     *
     * @param scopeClass A {@link Class} specifying {@link DependencyScope}.
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return The requested dependency. If {@code null} is returned, it indicates an error in
     * an {@link DependencyScope} implementation.
     */
    public static <T> T get(final Class<? extends DependencyScope> scopeClass, final Class<T> dependencyType) {
        return get(scopeClass, dependencyType, null);
    }

    /**
     * Gets a dependency of the specified type. The dependency is requested from
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
        DependencyScope scope = getScope(scopeClass, true);

        if (scope != null) {
            return scope.getDependency(dependencyType, dependant, false);
        }
        return null;
    }

    /**
     * Gets a dependency of the specified type. The dependency is requested from
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
     * Gets a dependency of the specified type. The dependency is requested from
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
     * Gets a dependency of the specified type. The dependency is requested from
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

////////////////

    /**
     * Gets all dependencies of the specified type. The dependencies are requested from
     * the currently active {@link DependencyScope}.
     *
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return A {@link Collection} containing the found dependencies. If an empty {@link Collection} is returned,
     * it indicates an error in an {@link DependencyScope} implementation or configuration.
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T> Collection<T> getAll(final Class<T> dependencyType) {
        final DependencyQuery<T> query = DependencyQuery.findAll(dependencyType);
        getActiveScope().getDependencies(query, null);
        return query.getFoundDependencies();
    }

    /**
     * Gets all dependency of the specified type. The dependencies are requested from
     * the given {@link DependencyScope}.
     *
     * @param scopeType A {@link Class} specifying {@link DependencyScope}.
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return A {@link Collection} containing the found dependencies. If an empty {@link Collection} is returned,
     * it indicates an error in an {@link DependencyScope} implementation or configuration.
     */
    @SuppressWarnings("unused")
    public static <T> Collection<T> getAll(final Class<? extends DependencyScope> scopeType, final Class<T> dependencyType) {
        return getAll(scopeType, dependencyType, null);
    }

    /**
     * Gets all dependencies of the specified type. The dependencies are requested from
     * the specified {@link DependencyScope}.
     *
     * @param scopeClass A {@link Class} specifying {@link DependencyScope}.
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param dependant      The object requesting the requested. This parameter is required when the requesting object
     *                       is also a requested within the object graph represented by the active {@link Dependency}.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return A {@link Collection} containing the found dependencies. If an empty {@link Collection} is returned,
     * it indicates an error in an {@link DependencyScope} implementation or configuration.
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T> Collection<T> getAll(final Class<? extends DependencyScope> scopeClass, final Class<T> dependencyType, final Object dependant) {
        final DependencyScope scope = getScope(scopeClass, true);
        final DependencyQuery<T> query = DependencyQuery.findAll(dependencyType);

        if (scope != null) {
            scope.getDependencies(query, dependant);
        }
        return query.getFoundDependencies();
    }

    /**
     * Gets all dependencies of the specified type. The dependencies are requested from
     * the given {@link DependencyScope}.
     *
     * @param scope A {@link DependencyScope}.
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return A {@link Collection} containing the found dependencies. If an empty {@link Collection} is returned,
     * it indicates an error in an {@link DependencyScope} implementation or configuration.
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T> Collection<T> getAll(final DependencyScope scope, final Class<T> dependencyType) {
        final DependencyQuery<T> query = DependencyQuery.findAll(dependencyType);
        scope.getDependencies(query, null);
        return query.getFoundDependencies();
    }

    /**
     * Gets all dependencies of the specified type. The dependencies are requested from
     * the currently active {@link DependencyScope}.
     *
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param dependant      The object requesting the requested. This parameter is required when the requesting object
     *                       is also a requested within the object graph represented by the active {@link Dependency}.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return A {@link Collection} containing the found dependencies. If an empty {@link Collection} is returned,
     * it indicates an error in an {@link DependencyScope} implementation or configuration.
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T> Collection<T> getAll(final Class<T> dependencyType, final Object dependant) {
        final DependencyQuery<T> query = DependencyQuery.findAll(dependencyType);
        getActiveScope().getDependencies(query, dependant);
        return query.getFoundDependencies();
    }

    /**
     * Gets all dependencies of the specified type. The dependencies are requested from
     * the currently active {@link DependencyScope}.
     *
     * @param scope A {@link DependencyScope}.
     * @param dependencyType A {@link Class} specifying the type of the requested dependency.
     * @param dependant      The object requesting the requested. This parameter is required when the requesting object
     *                       is also a requested within the object graph represented by the active {@link Dependency}.
     * @param <T>            A type parameter for casting the requested dependency to expected type.
     * @return A {@link Collection} containing the found dependencies. If an empty {@link Collection} is returned,
     * it indicates an error in an {@link DependencyScope} implementation or configuration.
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T> Collection<T> getAll(final DependencyScope scope, final Class<T> dependencyType, final Object dependant) {
        final DependencyQuery<T> query = DependencyQuery.findAll(dependencyType);
        scope.getDependencies(query, dependant);
        return query.getFoundDependencies();
    }

    /**
     * This method resets the application level {@link DependencyScope} to {@code null}.
     * This method is added only for testing purposes.
     */
    protected static void resetAppScope() {
        appScope = null;
    }

    /**
     * This method resets the currently active {@link DependencyScope} to {@code null}.
     * This method is added only for testing purposes.
     */
    public static void resetActiveScope() {
        activeScope = null;
    }
}
