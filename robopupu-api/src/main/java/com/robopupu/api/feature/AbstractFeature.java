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
package com.robopupu.api.feature;

import android.support.annotation.CallSuper;

import com.robopupu.api.dependency.D;
import com.robopupu.api.dependency.Dependency;
import com.robopupu.api.dependency.DependencyScope;
import com.robopupu.api.dependency.DependencyScopeOwner;
import com.robopupu.api.mvp.Presenter;
import com.robopupu.api.plugin.AbstractPluginStateComponent;
import com.robopupu.api.plugin.PlugInvoker;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.api.util.Params;
import com.robopupu.api.util.StringToolkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link AbstractFeature} provides an abstract base class for implementing {@link Feature}s.
 */
public abstract class AbstractFeature extends AbstractPluginStateComponent
        implements Feature, DependencyScopeOwner {

    protected final ArrayList<FeatureView> mActiveViews;
    protected final HashMap<String, FeatureView> mBackStackViews;

    protected int mFeatureContainerId;
    protected FeatureManager mFeatureManager;
    protected DependencyScope mFeatureScope;
    protected boolean mIsActivityFeature;
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
        mIsActivityFeature = isActivityFeature;
        mActiveViews = new ArrayList<>();
        mBackStackViews = new HashMap<>();
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
            mFeatureScope = D.getScope(mScopeClass, true);
        }
        return mFeatureScope;
    }

    @Override
    public final List<FeatureView> getActiveViews() {
        return mActiveViews;
    }

    @Override
    public final FeatureView addActiveView(final FeatureView view) {
        if (!mActiveViews.contains(view)) {
            mActiveViews.add(view);
            return view;
        }
        // Adding an active View implies that this Feature has resumed.
        resume();
        return null;
    }

    @Override
    public final FeatureView removeActiveView(final FeatureView view) {
        if (mActiveViews.contains(view)) {
            mActiveViews.remove(view);
            return view;
        }
        return null;
    }

    /**
     * Adds the given {@link FeatureView} to the {@link HashMap} that contains all
     * the {@link FeatureView}s of this {@link Feature} that has been added to back stack.
     * @param tag The tag of the added {@link FeatureView}.
     * @param view The {@link FeatureView} that has been added.
     */
    protected final void addBackStackView(final String tag, final FeatureView view) {
        if (!mBackStackViews.containsKey(tag)) {
            mBackStackViews.put(tag, view);
        }
    }

    /**
     * Removes the specified {@link FeatureView} from the {@link HashMap} that contains all the
     * {@link FeatureView}s of this {@link Feature} that has been added to back stack.
     * @param tag The tag specifying the {@link FeatureView} to be removed.
     */
    protected final void removeBackStackView(final String tag) {
        mBackStackViews.remove(tag);
    }

    @Override
    public boolean hasForegroundView() {
        return !mActiveViews.isEmpty();
    }

    @Override
    public void setActivityFeature(boolean isActivityFeature) {
        mIsActivityFeature = isActivityFeature;
    }

    @Override
    public boolean isActivityFeature() {
        return mIsActivityFeature;
    }

    @Override
    public boolean isActiveView(final FeatureView view) {
        return mActiveViews.contains(view);
    }

    @Override
    public void setFeatureManager(final FeatureManager manager) {
        mFeatureManager = manager;
    }

    @Override
    public FeatureContainer getFeatureContainer() {
        return mFeatureManager.getFeatureContainer(mFeatureContainerId);
    }

    @Override
    public void setFeatureContainer(final FeatureContainer container) {
        mFeatureContainerId = container.getContainerViewId();
    }

    /**
     * Shows the {@link FeatureView} attached to the specified {@link Presenter}.
     * @param presenterClass A {@link Class} specifying the {@link Presenter}.
     * @param addToBackStack A {@code boolean} value specifying if the {@link FeatureView} is added
     *                       to back stack.
     * @param params Optional {@link Params}.
     * @return A {@link FeatureView}. May be {@link PlugInvoker}.
     */
    @SuppressWarnings("unchecked")
    protected FeatureView showView(final Class<? extends Presenter> presenterClass,
                            final boolean addToBackStack,
                            final Params... params) {
        return showView(getFeatureContainer(), presenterClass, addToBackStack, null, params);
    }

    /**
     * Shows the {@link FeatureView} attached to the specified {@link Presenter}.
     * The {@link FeatureView} is shown using the given {@link FeatureTransitionManager}.
     *
     * @param transitionManager A {@link FeatureTransitionManager}.
     * @param presenterClass A {@link Class} specifying the {@link Presenter}.
     * @param addToBackStack A {@code boolean} value specifying if the {@link FeatureView} is to
     *                       be added to back stack.
     * @param tag A tag for the {@link FeatureView} used for {@code FragmentTransaction}.
     *            May be {@code null}.
     * @param params Optional {@link Params}.
     * @return A {@link FeatureView}. May be {@link PlugInvoker}.
     */
    @SuppressWarnings("unchecked")
    protected FeatureView showView(final FeatureTransitionManager transitionManager,
                            final Class<? extends Presenter> presenterClass,
                            final boolean addToBackStack,
                            final String tag,
                            final Params... params) {
        final Presenter presenter = plug(presenterClass);

        if (Params.containsValues(params)) {
            final Params presenterParams = Params.merge(params);
            presenter.setParams(presenterParams);
        }

        final FeatureView view = (FeatureView)presenter.getView();
        view.setFeature(this);
        transitionManager.showView(view, addToBackStack, tag);

        if (!view.isDialog()) {
            addBackStackView(tag, view);
        }
        return view;
    }

    /**
     * Hides the {@link FeatureView} attached to the given {@link Presenter}.
     * @param presenterClass A {@link Presenter}.
     * @param addedToBackStack A {@code boolean} value specifying if the {@link FeatureView} was
     *                  added to backstack.
     * @param tag A tag for the {@link FeatureView} used for {@code FragmentTransaction}.
     *            May be {@code null}.
     */
    @SuppressWarnings("unchecked")
    protected void hideView(final Class<? extends Presenter> presenterClass,
                            final boolean addedToBackStack,
                            final String tag) {
        final FeaturePresenter presenter = plug(presenterClass);
        hideView(presenter, addedToBackStack, tag);
    }

    /**
     * Hides the {@link FeatureView} attached to the given {@link Presenter}.
     * @param presenter A {@link Presenter}.
     * @param addedToBackStack A {@code boolean} value specifying if the {@link FeatureView} was
     *                  added to backstack.
     * @param tag A tag for the {@link FeatureView} used for {@code FragmentTransaction}.
     *            May be {@code null}.
     */
    @SuppressWarnings("unchecked")
    protected void hideView(final FeaturePresenter presenter,
                            final boolean addedToBackStack,
                            final String tag) {
        final FeatureView view = (FeatureView)presenter.getView();
        getFeatureContainer().removeView(view, addedToBackStack, tag);
        removeBackStackView(tag);
    }

    @Override
    public void clearBackStack() {
        final FeatureContainer container = getFeatureContainer();
        if (container != null) {

            if (!mBackStackViews.isEmpty()) {
                container.clearBackStack(mBackStackViews);
            }
        }
    }

    @Override
    @SuppressWarnings("uncheckked")
    public void goBack() {
        final String tag = getFeatureContainer().goBack();

        if (StringToolkit.isNotEmpty(tag)) {
            mBackStackViews.remove(tag);
        }
    }

    @SuppressWarnings("unchecked")
    protected static Class<? extends FeatureView> getClass(final FeatureView view) {

        final Class viewClass = view.getClass();

        if (viewClass.isInterface() && FeatureView.class.isAssignableFrom(viewClass)) {
            return (Class<? extends FeatureView>)viewClass;
        }

        final Class[] interfaceClasses = viewClass.getInterfaces();

        for (Class interfaceClass : interfaceClasses) {
            if (FeatureView.class.isAssignableFrom(interfaceClass)) {
                return (Class<? extends FeatureView>)interfaceClass;
            }
        }
        return null;
    }

    @Override
    public boolean isBackPressedEventHandler() {
        return true;
    }

    @Override
    public boolean hasBackStackViews() {
        return !mBackStackViews.isEmpty();
    }

    @Override
    public boolean canGoBack() {
        return getFeatureContainer().canGoBack();
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
    @CallSuper
    public final void pause() {
        pause(false);
    }

    @Override
    @CallSuper
    public final void pause(final boolean finishing) {
        if (isStopped()) {
            throw new IllegalStateException("A stopped or destroyed feature cannot be paused");
        }
        super.pause();
        onPause(finishing);
        mFeatureManager.onFeaturePaused(this, finishing);
    }

    @Override
    @CallSuper
    public final void resume() {
        if (isStopped()) {
            throw new IllegalStateException("A stopped or destroyed feature cannot be resumed");
        }
        super.resume();
        Dependency.activateScope(this);
        mFeatureManager.onFeatureResumed(this);
    }

    @Override
    @CallSuper
    public final void stop() {
        if (!isStopped()) {
            mFeatureManager.onFeatureStopped(this);
            super.stop();
        }
    }

    /**
     * Finishes this {@link Feature}
     */
    @Override
    public final void finish() {
        stop();
    }

    @Override
    @CallSuper
    public final void destroy() {
        if (!isDestroyed()) {

            if (!isStopped()) {
                stop();
            }

            super.destroy();

            Dependency.disposeScope(this);

            if (hasBackStackViews()) {
                clearBackStack();
                mBackStackViews.clear();
            }

            mFeatureManager.onFeatureDestroyed(this);
            mFeatureManager = null;
            mActiveViews.clear();
        }
    }

    /**
     * Invoked by {@link Feature#pause(boolean)}.
     *
     * @param finishing A {@code boolean} value indicating if the {@link Feature} is
     *                  going to be finished i.e. it is not resumed nor restarted anymore.
     */
    @SuppressWarnings("unused")
    protected void onPause(boolean finishing) {
        // By default do nothing
    }

    @Override
    public void onPresenterFinished(Presenter presenter) {
        // By default do nothing
    }

    @Override
    public void onFeatureContainerPaused(FeatureContainer container, boolean finishing) {
        // By default do nothing
    }

    @Override
    public void onFeatureContainerResumed(FeatureContainer container) {
        // By default do nothing
    }

    @Override
    public void onFeatureContainerStarted(FeatureContainer container) {
        // By default do nothing
    }

    @Override
    public void onFeatureContainerStopped(FeatureContainer container, boolean finishing) {
        // By default do nothing
    }
}
