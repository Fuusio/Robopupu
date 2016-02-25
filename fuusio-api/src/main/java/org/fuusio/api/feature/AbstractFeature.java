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
package org.fuusio.api.feature;

import android.support.annotation.CallSuper;
import android.support.v4.app.FragmentManager;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.Dependency;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.DependencyScopeOwner;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.AbstractPluginStateComponent;
import org.fuusio.api.plugin.PlugInvoker;
import org.fuusio.api.plugin.PluginBus;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AbstractFeature} provides an abstract base class for implementing {@link Feature}s.
 */
public abstract class AbstractFeature extends AbstractPluginStateComponent
        implements Feature, DependencyScopeOwner {

    protected final ArrayList<View> mActiveViews;

    protected boolean mActivityFeature;
    protected int mBackStackSize;
    protected FeatureContainer mFeatureContainer;
    protected FeatureManager mFeatureManager;
    protected DependencyScope mFeatureScope;
    protected Class<? extends DependencyScope> mScopeClass;

    /**
     * Construct a new instance of {@link AbstractFeature}.
     *
     * @param scopeClass The {@link Class} of the owned {@link DependencyScope}.
     */
    protected AbstractFeature(final Class<? extends DependencyScope> scopeClass) {
        this(scopeClass, false);
    }

    /**
     * Construct a new instance of {@link AbstractFeature}.
     *
     * @param scopeClass The {@link Class} of the owned {@link DependencyScope}.
     * @param isActivityFeature A {@code boolean} flag specifying if this {@link AbstractFeature}
     *                          is an Activity Feature.
     */
    protected AbstractFeature(final Class<? extends DependencyScope> scopeClass, final boolean isActivityFeature) {
        mScopeClass = scopeClass;
        mActivityFeature = isActivityFeature;
        mActiveViews = new ArrayList<>();
        mBackStackSize = 0;
    }

    @Override
    public Class<? extends DependencyScope> getScopeClass() {
        return mScopeClass;
    }

    @Override
    public DependencyScope getOwnedScope() {
        return getFeatureScope();
    }

    protected DependencyScope getFeatureScope() {
        if (mFeatureScope == null) {
            mFeatureScope = Dependency.getScope(mScopeClass);
        }
        return mFeatureScope;
    }

    @Override
    public final List<View> getActiveViews() {
        return mActiveViews;
    }


    @Override
    public final View addActiveView(final View view) {
        if (!mActiveViews.contains(view)) {
            mActiveViews.add(view);
            return view;
        }
        return null;
    }

    @Override
    public final View removeActiveView(final View view) {
        if (mActiveViews.contains(view)) {
            mActiveViews.remove(view);
            return view;
        }
        return null;
    }

    @Override
    public boolean hasFocusedView() {
        if (!mActiveViews.isEmpty()) {

            for (int i = mActiveViews.size() - 1; i >= 0; i--) {
                final View view = mActiveViews.get(i);

                if (view.hasFocus()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasForegroundView() {
        return !mActiveViews.isEmpty();
    }

    @Override
    public void setActivityFeature(boolean isActivityFeature) {
        mActivityFeature = isActivityFeature;
    }

    @Override
    public boolean isActivityFeature() {
        return mActivityFeature;
    }

    @Override
    public boolean isActiveView(final View view) {
        return mActiveViews.contains(view);
    }

    @Override
    public final FeatureManager getFeatureManager() {
        return mFeatureManager;
    }

    @Override
    public void setFeatureManager(final FeatureManager manager) {
        mFeatureManager = manager;
    }

    @Override
    public FeatureContainer getFeatureContainer() {
        return mFeatureContainer;
    }

    @Override
    public void setFeatureContainer(final FeatureContainer container) {
        mFeatureContainer = container;
    }

    /**
     * Shows the {@link View} attached to the specified {@link Presenter}.
     * @param presenterClass A {@link Class} specifying the {@link Presenter}.
     * @return A reference to {@link View}. May be {@link PlugInvoker}.
     */
    @SuppressWarnings("unchecked")
    protected View showView(final Class<? extends Presenter> presenterClass) {
        return showView(mFeatureContainer, presenterClass);
    }

    /**
     * Shows the {@link View} attached to the specified {@link Presenter}. The {@link View} is
     * shown using the given {@link FeatureTransitionManager}.
     *
     * @param transitionManager A {@link FeatureTransitionManager}.
     * @param presenterClass A {@link Class} specifying the {@link Presenter}.
     * @return A reference to {@link View}. May be {@link PlugInvoker}.
     */
    @SuppressWarnings("unchecked")
    protected View showView(final FeatureTransitionManager transitionManager, final Class<? extends Presenter> presenterClass) {
        final Presenter presenter = plug(presenterClass);
        final View view = presenter.getView();

        if (view instanceof FeatureFragment) {
            final FeatureFragment fragment = (FeatureFragment) view;
            transitionManager.showFragment(fragment, null);
        } else if (view instanceof FeatureDialogFragment) {
            final FeatureDialogFragment fragment = (FeatureDialogFragment) view;
            transitionManager.showDialogFragment(fragment, null);
        }
        return view;
    }

    @Override
    public boolean activateView(final View view) {

        final Class<? extends View> viewClass = getClass(view);
        getScope().cache(viewClass, view);

        if (view instanceof FeatureFragment) {
            // XXX final FeatureFragment fragment = (FeatureFragment) view;
            // XXX fragment.setFeature(this);
            // XXX transitionManager.showFeatureFragment(this, fragment, fragment.getTag());
        } else {
            throw new IllegalStateException("Fragment has to be derived from org.fuusio.api.flow.FlowFragment class");
        }

        // TODO

        //final Presenter presenter = view.getPresenter();
        //presenter.addListener(this);

        return true;
    }

    /**
     * Activates the {@link View} specified by the given class.
     * @param viewClass A {@link Class} specifying the {@link View} to be activated.
     * @return The activated {@link View}.
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T activateView(final Class<? extends View> viewClass) {
        final View view = D.get(viewClass, getScope());

        if (activateView(view)) {
            return (T)view;
        } else {
            return null;
        }
    }

    @Override
    public void clearBackStack() {
        if (mFeatureContainer != null) {
            final FragmentManager manager = mFeatureContainer.getSupportFragmentManager();

            if (manager.getBackStackEntryCount() > 0) {
                manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            mBackStackSize = 0;
        }
    }

    @Override
    @SuppressWarnings("uncheckked")
    public void goBack() {
        goBack(mFeatureContainer);
    }

    @Override
    public void goBack(final FeatureTransitionManager transitionManager) {
        final FragmentManager fragmentManager = mFeatureContainer.getSupportFragmentManager();
        final int count = fragmentManager.getBackStackEntryCount();

        if (count > 0) {
            fragmentManager.popBackStack();
        }
        // REMOVE mBackStackSize = fragmentManager.getBackStackEntryCount();
    }

    @SuppressWarnings("unchecked")
    protected static Class<? extends View> getClass(final View view) {

        final Class viewClass = view.getClass();

        if (viewClass.isInterface() && View.class.isAssignableFrom(viewClass)) {
            return (Class<? extends View>)viewClass;
        }

        final Class[] interfaceClasses = viewClass.getInterfaces();

        for (Class interfaceClass : interfaceClasses) {
            if (View.class.isAssignableFrom(interfaceClass)) {
                return (Class<? extends View>)interfaceClass;
            }
        }
        return null;
    }

    @Override
    public boolean isBackPressedEventHandler() {
        return true;
    }

    @Override
    public boolean hasViewsInBackStack() {
        return !mActiveViews.isEmpty();
    }

    @Override
    public boolean canGoBack() {
        final FragmentManager fragmentManager = null; // getFeatureContainer().getSupportFragmentManager();
        final int count = fragmentManager.getBackStackEntryCount();
        return (count > 1);
    }

    /**
     * Plugs an instance of specific {@link Class} to {@link PluginBus}.
     * @param pluginClass A  {@link Class}
     * @return The plugged instance as an {@link Object}
     */
    @SuppressWarnings("unchecked")
    public <T> T plug(final Class<?> pluginClass) {
        T plugin = (T)D.get(getFeatureScope(), pluginClass);
        PluginBus.plug(plugin);
        return plugin;
    }

    /**
     * Plugs the the given plugin {@link Object} to this {@link PluginBus}.
     * @param plugin A plugin {@link Object}.
     */
    @SuppressWarnings("unchecked")
    public <T> T  plug(final Object plugin) {
        PluginBus.plug(plugin);
        return (T)plugin;
    }

    /**
     * Unplugs the the given plugin {@link Object} to this {@link PluginBus}.
     * @param plugin A plugin {@link Object}.
     */
    @SuppressWarnings("unchecked")
    public void unplug(final Object plugin) {
        PluginBus.unplug(plugin);
    }

    @Override
    public void onUnplugged(final PluginBus bus) {
        if (mFeatureScope != null) {
            D.disposeScope(this);
        }
    }

    @Override
    @CallSuper
    public final void pause() {
        super.pause();
        mFeatureManager.onFeaturePaused(this);
    }

    @Override
    @CallSuper
    public final void resume() {
        super.resume();
        mFeatureManager.onFeatureResumed(this);
    }

    @Override
    @CallSuper
    public final void stop() {
        if (!isStopped() && !isDestroyed()) {
            mFeatureManager.onFeatureStopped(this);
            super.stop();
        }
    }

    /**
     * Finishes this {@link Feature}
     */
    @Override
    public void finish() {
        stop();
    }

    @Override
    @CallSuper
    public final void destroy() {
        if (!isDestroyed()) {
            super.destroy();

            Dependency.disposeScope(this);

            if (hasViewsInBackStack()) {
                clearBackStack();
            }

            mFeatureManager.onFeatureDestroyed(this);
            mFeatureManager = null;
            mFeatureContainer = null;
            mActiveViews.clear();
        }
    }

    @Override
    public void onPresenterStarted(Presenter presenter) {
        // By default do nothing
    }

    @Override
    public void onPresenterResumed(Presenter presenter) {
        // By default do nothing
    }

    @Override
    public void onPresenterPaused(Presenter presenter) {
        // By default do nothing
    }

    @Override
    public void onPresenterStopped(Presenter presenter) {
        // By default do nothing
    }

    @Override
    public void onPresenterDestroyed(Presenter presenter) {
        // By default do nothing
    }

    @Override
    public void onPresenterFinished(Presenter presenter) {
        // By default do nothing
    }
}
