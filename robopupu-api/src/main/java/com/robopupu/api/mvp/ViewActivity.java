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

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.api.util.Converter;
import com.robopupu.api.util.PermissionRequestManager;

/**
 * {@link ViewActivity} provides an abstract base class for concrete {@link Activity}
 * implementations that implement {@link View} components for a MVP architectural pattern
 * implementation.
 *
 * @param <T_Presenter> The type of the {@link Presenter}.
 */
public abstract class ViewActivity<T_Presenter extends Presenter> extends AppCompatActivity
        implements View, PresentedView<T_Presenter>, Scopeable {

    private static String TAG = ViewDialogFragment.class.getSimpleName();

    protected final ViewBinder mBinder;
    protected final ViewState mState;

    protected DependencyScope mScope;

    protected ViewActivity() {
        mBinder = new ViewBinder(this);
        mState = new ViewState(this);
    }

    /**
     * Gets the {@link Presenter} assigned for this {@link ViewActivity}.
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
     * Resolves the {@link Presenter} assigned for this {@link ViewActivity}.
     *
     * @return A {@link Presenter}.
     */
    @SuppressWarnings("unchecked")
    protected T_Presenter resolvePresenter() {
        return getPresenter();
    }

    @Override
    protected void onCreate(final Bundle inState) {
        super.onCreate(inState);

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewCreated(this, Converter.fromBundleToParams(inState));
        } else {
            Log.d(TAG, "onViewCreated(...) : Presenter == null");
        }

        mBinder.setActivity(this);
    }

    @Override
    protected void onPostCreate(final Bundle inState) {
        super.onPostCreate(inState);
        onCreateBindings();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mState.onStart();

        if (!mState.isRestarted()) {
            onCreateBindings();
        }

        final DependenciesCache cache = D.get(DependenciesCache.class);
        final DependencyMap dependencies = cache.getDependencies(this);

        if (dependencies != null) {
            onRestoreDependencies(dependencies);
        }

        // Presenter as dependency is automatically saved into DependenciesCache so that it can be
        // restored when and if the View resumes. Invoking getPresenter method takes care of restoring
        // the reference to Presenter

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewStart(this);
            mBinder.initialise();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mState.onResume();

        final DependenciesCache cache = D.get(DependenciesCache.class);
        cache.removeDependencies(this);

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewResume(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mState.onStop();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewStop(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mState.onPause();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewPause(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mState.onDestroy();
        mBinder.dispose();

        if (this instanceof DependencyScopeOwner) {

            // Cached DependencyScope is automatically disposed to avoid memory leaks

            final DependenciesCache cache = D.get(DependenciesCache.class);
            final DependencyScopeOwner owner = (DependencyScopeOwner) this;
            cache.removeDependencyScope(owner);
        }

        if (PluginBus.isPlugged(this)) {
            PluginBus.unplug(this);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mState.onRestart();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        mState.setInstanceStateSaved(true);

        onSaveState(outState);

        final DependenciesCache cache = D.get(DependenciesCache.class);
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
     * This method can be overridden to save state of this {@link ViewActivity} to the given
     * {@link Bundle}.
     * @param outState A {@link Bundle}.
     */
    protected void onSaveState(final Bundle outState) {
        // By default do nothing
    }

    @Override
    public void onRestoreInstanceState(final Bundle inState) {
        super.onRestoreInstanceState(inState);
        mState.setInstanceStateSaved(false);

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

    /**
     * This method can be overridden to restore state of this {@link ViewActivity} from the given
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

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        final PermissionRequestManager manager = D.get(PermissionRequestManager.class);
        manager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public ViewState getState() {
        return mState;
    }

    public boolean canCommitFragment() {
        return mState.canCommitFragment();
    }

    /**
     * Invoked to bind {@link ViewBinding}s to {@link View}s. This method has to be overridden in
     * classes extended from {@link ViewFragment}.
     */
    @CallSuper
    protected void onCreateBindings() {
        // Do nothing by default
    }

    /**
     * Looks up and returns a {@link android.view.View} with the given layout id.
     *
     * @param viewId A view id used in a layout XML resource.
     * @return The found {@link android.view.View}.
     */
    @SuppressWarnings("unchecked")
    public <T extends android.view.View> T getView(@IdRes final int viewId) {
        return (T) findViewById(viewId);
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
