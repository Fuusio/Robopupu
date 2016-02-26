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
package org.fuusio.api.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;

import org.fuusio.api.binding.AdapterViewBinding;
import org.fuusio.api.binding.ViewBinder;
import org.fuusio.api.binding.ViewBinding;
import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependenciesCache;
import org.fuusio.api.dependency.DependencyMap;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.DependencyScopeOwner;
import org.fuusio.api.dependency.Scopeable;

/**
 * {@link ViewFragment} provides an abstract base class for concrete {@link Fragment} implementations
 * that implement {@link View} components for a MVP architectural pattern implementation.
 *
 * @param <T_Presenter> The type of the {@link Presenter}.
 */
public abstract class ViewFragment<T_Presenter extends Presenter> extends Fragment
        implements View, Scopeable {

    private final ViewBinder mBinder;
    private final ViewState mState;

    private DependencyScope mScope;

    protected ViewFragment() {
        mBinder = new ViewBinder();
        mState = new ViewState(this);
    }

    /**
     * Gets the {@link Presenter} assigned for this {@link ViewActivity}.
     *
     * @return A {@link Presenter}.
     */
    protected abstract T_Presenter getPresenter();

    /**
     * Resolves the {@link Presenter} assigned for this {@link ViewActivity}.
     *
     * @return A {@link Presenter}.
     */
    @SuppressWarnings("unchecked")
    protected T_Presenter resolvePresenter() {
        T_Presenter presenter = getPresenter();

        if (presenter == null) {

            final DependenciesCache cache = D.get(DependenciesCache.class);
            final DependencyMap dependencies = cache.getDependencies(this);

            if (dependencies != null) {
                presenter = dependencies.getDependency(KEY_DEPENDENCY_PRESENTER);
            }
        }
        return presenter;
    }

    @NonNull
    @Override
    public String getDependenciesKey() {
        return getClass().getName();
    }

    /**
     * Test if this {@link View} has a focus.
     * @return A {@code boolean}.
     */
    @Override
    public boolean hasFocus() {
        final Activity activity = getActivity();

        if (activity instanceof ViewActivity) {
            if (((ViewActivity)activity).hasFocus()) {
                return mState.isResumed();
            }
        }
        return false;
    }

    @Override
    public void onViewCreated(final android.view.View view, final Bundle inState) {
        super.onViewCreated(view, inState);
        mState.onCreate();

        // We used getPresenter() getter to access the Presenter to guarantee that a reference
        // for it is initialised or restored

        resolvePresenter().onViewCreated(this, inState);
    }

    @Override
    public void onActivityCreated(final Bundle inState) {
        super.onActivityCreated(inState);

        final DependenciesCache cache = D.get(DependenciesCache.class);

        if (this instanceof DependencyScopeOwner) {
            final DependencyScopeOwner owner = (DependencyScopeOwner) this;

            // DependencyScope is automatically restored and activated

            /* XXX
            if (cache.containsDependencyScope(owner)) {
                final DependencyScope scope = cache.removeDependencyScope(owner);
                D.activateScope(owner, scope);
            } else {
                D.activateScope(owner);
            }*/
        }

        mBinder.setActivity(getActivity());
        createBindings();

        if (inState != null) {
            onRestoreState(inState);

            final DependencyMap dependencies = cache.getDependencies(this);

            if (dependencies != null) {

                final T_Presenter presenter = dependencies.getDependency(KEY_DEPENDENCY_PRESENTER);

                // TODO

                final DependencyScope scope = dependencies.getDependency(KEY_DEPENDENCY_SCOPE);

                if (scope != null) {
                    mScope = scope;
                }

                onRestoreDependencies(dependencies);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mState.onStart();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewStart(this);
        }
    }

    /**
     * Invoked to bind {@link ViewBinding}s to {@link View}s. This method has to be overridden in
     * classes extended from {@link ViewFragment}.
     */
    protected void createBindings() {
        // Do nothing by default
    }

    @Override
    public void onResume() {
        super.onResume();
        mState.onResume();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewResume(this);
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        mState.onStop();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewStop(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mState.onPause();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewPause(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mState.onDestroy();

        mBinder.dispose();

        final DependenciesCache cache = D.get(DependenciesCache.class);
        cache.removeDependencies(this);

        if (this instanceof DependencyScopeOwner) {

            // Cached DependencyScope is automatically disposed to avoid memory leaks

            final DependencyScopeOwner owner = (DependencyScopeOwner) this;
            cache.removeDependencyScope(owner);
        }

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewDestroy(this);
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        onSaveState(outState);

        final DependenciesCache cache = D.get(DependenciesCache.class);

        // Save a reference to the Presenter

        final DependencyMap dependencies = cache.getDependencies(this, true);
        dependencies.addDependency(KEY_DEPENDENCY_PRESENTER, getPresenter());
        dependencies.addDependency(KEY_DEPENDENCY_SCOPE, mScope);

        onSaveDependencies(dependencies);

        if (this instanceof DependencyScopeOwner) {

            // DependencyScope is automatically cached so that it can be restored when
            // and if the View resumes

            final DependencyScopeOwner owner = (DependencyScopeOwner) this;
            cache.saveDependencyScope(owner, owner.getOwnedScope());
        }
    }

    /**
     * This method can be overridden to save state of this {@link ViewFragment} to the given
     * {@link Bundle}.
     * @param outState A {@link Bundle}.
     */
    protected void onSaveState(final Bundle outState) {
        // By default do nothing
    }

    /**
     * This method can be overridden to restore state of this {@link ViewFragment} from the given
     * {@link Bundle}.
     * @param inState A {@link Bundle}.
     */
    protected void onRestoreState(final Bundle inState) {
        // By default do nothing
    }

    /**
     * This method can be overridden to save dependencies after the {@link ViewFragment} is
     * restored, for instance, after recreating it.
     *
     * @param dependencies A {@link DependencyMap} for saving the dependencies.
     */
    protected void onSaveDependencies(final DependencyMap dependencies) {
        // By default do nothing
    }

    /**
     * This method can be overridden to restore dependencies after the {@link ViewFragment} is
     * restored, for instance, after recreating it.
     *
     * @param dependencies A {@link DependencyMap} for restoring the dependencies.
     */
    protected void onRestoreDependencies(final DependencyMap dependencies) {
        // By default do nothing
    }

    @NonNull
    @Override
    public ViewState getState() {
        return mState;
    }

    /**
     * Looks up and returns a {@link android.view.View} with the given layout id.
     *
     * @param viewId A view id used in a layout XML resource.
     * @return The found {@link android.view.View}.
     */
    @SuppressWarnings("unchecked")
    public <T extends android.view.View> T getView(@IdRes final int viewId) {
        return (T) getActivity().findViewById(viewId);
    }

    /**
     * Creates and binds a {@link ViewBinding} to a {@link android.view.View} specified by the given view id.
     *
     * @param viewId A view id used in a layout XML resource.
     * @param <T>    The parametrised type of the ViewDelagate.
     * @return The created {@link ViewBinding}.
     */
    @SuppressWarnings("unchecked")
    public <T extends ViewBinding<?>> T bind(@IdRes final int viewId) {
        return mBinder.bind(viewId);
    }

    /**
     * Binds the given {@link ViewBinding} to the specified {@link android.view.View}.
     *
     * @param viewId  A view id in a layout XML specifying the target {@link android.view.View}.
     * @param binding An {@link ViewBinding}.
     * @return The found and bound {@link android.view.View}.
     */
    @SuppressWarnings("unchecked")
    public <T extends android.view.View> T bind(@IdRes final int viewId, final ViewBinding<T> binding) {
        return mBinder.bind(viewId, binding);
    }

    /**
     * Binds the given {@link AdapterViewBinding} to the specified {@link AdapterView}.
     *
     * @param viewId  A view id in a layout XML specifying the target {@link AdapterView}.
     * @param binding An {@link AdapterViewBinding}.
     * @param adapter An {@link AdapterViewBinding.Adapter} that is assigned to {@link AdapterViewBinding}.
     * @return The found and bound {@link AdapterView}.
     */
    @SuppressWarnings("unchecked")
    public AdapterView bind(@IdRes final int viewId, final AdapterViewBinding<?> binding, final AdapterViewBinding.Adapter<?> adapter) {
        return mBinder.bind(viewId, binding, adapter);
    }

    @Override
    public DependencyScope getScope() {
        return mScope;
    }

    @Override
    public void setScope(final DependencyScope scope) {
        mScope = scope;
    }
}
