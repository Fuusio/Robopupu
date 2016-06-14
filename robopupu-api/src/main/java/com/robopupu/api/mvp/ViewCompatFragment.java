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
package com.robopupu.api.mvp;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.AdapterView;

import com.robopupu.api.binding.AdapterViewBinding;
import com.robopupu.api.binding.ViewBinder;
import com.robopupu.api.binding.ViewBinding;
import com.robopupu.api.dependency.D;
import com.robopupu.api.dependency.DependenciesCache;
import com.robopupu.api.dependency.DependencyMap;
import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.dependency.DependencyScopeOwner;
import com.robopupu.api.dependency.Scopeable;
import com.robopupu.api.feature.FeatureContainerProvider;
import com.robopupu.api.feature.FeatureManager;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.api.util.Converter;
import com.robopupu.api.util.Utils;

/**
 * {@link ViewCompatFragment} provides an abstract base class for concrete {@link Fragment} implementations
 * that are used as {@link View} components in Robopupu MVP implementation.
 *
 * @param <T_Presenter> The parametrised type of the {@link Presenter}.
 */
public abstract class ViewCompatFragment<T_Presenter extends Presenter> extends Fragment
        implements View, PresentedView<T_Presenter>, Scopeable {

    private static String TAG = Utils.tag(ViewCompatFragment.class);

    private final ViewBinder mBinder;
    private final ViewState mState;

    private DependencyScope mScope;

    protected ViewCompatFragment() {
        mBinder = new ViewBinder(this);
        mState = new ViewState(this);
    }

    /**
     * Gets the {@link Presenter} assigned for this {@link ViewCompatActivity}.
     *
     * @return A {@link Presenter}.
     */
    @Override
    public abstract T_Presenter getPresenter();

    @Override
    public String getViewTag() {
        return getClass().getName();
    }

    /**
     * Resolves the {@link Presenter} assigned for this {@link ViewCompatActivity}.
     *
     * @return A {@link Presenter}.
     */
    protected T_Presenter resolvePresenter() {
        T_Presenter presenter = getPresenter();

        if (presenter == null) {
            if (PluginBus.isPlugin(getClass())) {
                PluginBus.plug(this);
                presenter = getPresenter();
            }
        }
        return presenter;
    }

    @Override
    public void onViewCreated(final android.view.View view, final Bundle inState) {
        Log.d(TAG, "onViewCreated(...)");
        super.onViewCreated(view, inState);
        mState.onCreate();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewCreated(this, Converter.fromBundleToParams(inState));
        } else {
            Log.d(TAG, "onViewCreated(...) : Presenter == null");
        }
    }

    @Override
    public void onActivityCreated(final Bundle inState) {
        super.onActivityCreated(inState);

        mBinder.setActivity(getActivity());
        onCreateBindings();

        if (inState != null) {
            onRestoreState(inState);

            final DependenciesCache cache = D.get(DependenciesCache.class);
            final DependencyMap dependencies = cache.getDependencies(this);

            if (dependencies != null) {

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
        Log.d(TAG, "onStart()");
        super.onStart();
        mState.onStart();

        // If this ViewCompatFragment is a FeatureContainerProvider, it needs to be registered to
        // FeatureManager
        if (this instanceof FeatureContainerProvider) {
            final FeatureContainerProvider provider = (FeatureContainerProvider)this;
            final FeatureManager featureManager = D.get(FeatureManager.class);
            featureManager.registerFeatureContainerProvider(provider);
        }

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewStart(this);
            mBinder.initialise();
        }
    }

    /**
     * Invoked to bind {@link ViewBinding}s to {@link View}s. This method has to be overridden in
     * classes extended from {@link ViewCompatFragment}.
     */
    @CallSuper
    protected void onCreateBindings() {
        // Do nothing by default
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
        mState.onResume();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewResume(this);
        }

        final DependenciesCache cache = D.get(DependenciesCache.class);
        cache.removeDependencies(this);
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();
        mState.onStop();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewStop(this);
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
        mState.onPause();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewPause(this);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        mState.onDestroy();

        mBinder.dispose();

        if (this instanceof DependencyScopeOwner) {

            // Cached DependencyScope is automatically disposed to avoid memory leaks

            final DependenciesCache cache = D.get(DependenciesCache.class);
            final DependencyScopeOwner owner = (DependencyScopeOwner) this;
            cache.removeDependencyScope(owner);
        }

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewDestroy(this);
        }

        if (PluginBus.isPlugged(this)) {
            Log.d(TAG, "onDestroy() : Unplugged from PluginBus");
            PluginBus.unplug(this);
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        onSaveState(outState);

        final DependenciesCache cache = D.get(DependenciesCache.class);

        // Save a reference to the Presenter

        final DependencyMap dependencies = cache.getDependencies(this, true);
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
     * This method can be overridden to save state of this {@link ViewCompatFragment} to the given
     * {@link Bundle}.
     * @param outState A {@link Bundle}.
     */
    protected void onSaveState(final Bundle outState) {
        // By default do nothing
    }

    /**
     * This method can be overridden to restore state of this {@link ViewCompatFragment} from the given
     * {@link Bundle}.
     * @param inState A {@link Bundle}.
     */
    protected void onRestoreState(final Bundle inState) {
        // By default do nothing
    }

    /**
     * This method can be overridden to save dependencies after the {@link ViewCompatFragment} is
     * restored, for instance, after recreating it.
     *
     * @param dependencies A {@link DependencyMap} for saving the dependencies.
     */
    protected void onSaveDependencies(final DependencyMap dependencies) {
        // By default do nothing
    }

    /**
     * This method can be overridden to restore dependencies after the {@link ViewCompatFragment} is
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
