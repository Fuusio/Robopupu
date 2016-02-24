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
import org.fuusio.api.mvp.PresenterListener;
import org.fuusio.api.mvp.View;
import org.fuusio.api.mvp.ViewDialogFragment;
import org.fuusio.api.plugin.AbstractPluginStateComponent;
import org.fuusio.api.plugin.PluginBus;
import org.fuusio.api.util.Params;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AbstractFeature} provides an abstract base class for implementing {@link Feature}s.
 */
public abstract class AbstractFeature extends AbstractPluginStateComponent
        implements Feature, DependencyScopeOwner {

    protected final ArrayList<View> mActiveViews;

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
        this(scopeClass, null);
    }

    /**
     * Construct a new instance of {@link AbstractFeature} with the given {@link Params}.
     *
     * @param scopeClass The {@link Class} of the owned {@link DependencyScope}.
     * @param params A {@link Params} containing parameters for starting the {@link Feature}.
     */
    protected AbstractFeature(final Class<? extends DependencyScope> scopeClass, final Params params) {
        mScopeClass = scopeClass;
        mParams = params;
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

    /**
     * TODO
     * @param presenterClass
     */
    @SuppressWarnings("unchecked")
    protected void showView(final Class<? extends Presenter> presenterClass) {
        final Presenter presenter = plug(presenterClass);
        final View view = presenter.getView();

        if (view instanceof FeatureFragment) {
            final FeatureFragment fragment = (FeatureFragment) view;
            mFeatureContainer.showFeatureFragment(fragment, null);
        } else if (view instanceof ViewDialogFragment) {
            final ViewDialogFragment fragment = (ViewDialogFragment) view;
            mFeatureContainer.showDialogFragment(fragment, null);
        }
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
        final FragmentManager manager = null; // XXX getFeatureContainer().getSupportFragmentManager();

        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        mBackStackSize = 0;
    }

    @Override
    @SuppressWarnings("uncheckked")
    public void goBack() {
        goBack(mFeatureContainer);
    }

    @Override
    public void goBack(final FeatureTransitionManager transitionManager) {
        final FragmentManager fragmentManager = mFeatureContainer.getSupportFragmentManager();
        int index = fragmentManager.getBackStackEntryCount() - 1;

        while (index >= 0) {
            final FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(index);
            final String tag = entry.getName();
            final FeatureFragment fragment = (FeatureFragment) fragmentManager.findFragmentByTag(tag);

            if (isActiveView(fragment)) {
                fragmentManager.popBackStack();
                break;
            } else {
                index--;
            }
        }
        mBackStackSize = fragmentManager.getBackStackEntryCount();
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
        super.stop();
        mFeatureManager.onFeatureStopped(this);

        if (hasViewsInBackStack()) {
            clearBackStack();
        }
    }

    @Override
    @CallSuper
    public final void destroy() {
        super.destroy();

        // TODO  XXX Dependency.deactivateScope(this);
        mFeatureManager.onFeatureDestroyed(this);
        clearBackStack();
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        updateFeatureContainer(bus);
    }

    @Override
    public void onPluginPlugged(final Object plugin) {
        if (plugin instanceof FeatureContainer) {
            updateFeatureContainer(PluginBus.getInstance());
        }
    }

    protected void updateFeatureContainer(final PluginBus bus) {
        final List<FeatureContainer> featureContainers = bus.getPlugs(FeatureContainer.class, true);

        if (!featureContainers.isEmpty()) {
            mFeatureContainer = featureContainers.get(0);
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
