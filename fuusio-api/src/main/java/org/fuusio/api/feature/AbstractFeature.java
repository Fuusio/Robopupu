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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.Dependency;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.DependencyScopeOwner;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.AbstractPluginStateComponent;
import org.fuusio.api.util.L;
import org.fuusio.api.util.Params;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AbstractFeature} provides an abstract base class for implementing {@link Feature}s.
 */
public abstract class AbstractFeature extends AbstractPluginStateComponent
        implements Feature, Presenter.Listener, DependencyScopeOwner {

    private static final String SUFFIX_DEPENDENCY_SCOPE = "_DependencyScope";

    protected final ArrayList<View> mActiveViews;

    protected int mBackStackSize;
    protected FeatureContainer mFeatureContainer;
    protected FeatureManager mFeatureManager;

    /**
     * Construct a new instance of {@link AbstractFeature}.
     */
    protected AbstractFeature() {
        this(null, null);
    }

    /**
     * Construct a new instance of {@link AbstractFeature} with the given {@link FeatureContainer}.
     *
     * @param container A {@link FeatureContainer}.
     * @param params    A {@link Params} containing parameters for starting the {@link Feature}.
     */
    protected AbstractFeature(final FeatureContainer container, final Params params) {
        mFeatureContainer = container;
        mParams = params;
        mActiveViews = new ArrayList<>();
        mBackStackSize = 0;
    }

    @Override
    public DependencyScope getScope() {
        DependencyScope scope = super.getScope();

        if (scope == null) {
            scope = Dependency.getScope(this);

            if (scope == null) {
                scope = createDependencyScope();
                scope.initialize();
            }
            setScope(scope);
        }
        return scope;
    }

    @Override
    public String getScopeId() {
        return getClass().getSimpleName();
    }

    @Override
    public final List<View> getActiveViews() {
        return mActiveViews;
    }


    protected FeatureContainer getFeatureContainer() { // TODO
        if (mFeatureContainer == null) {
            mFeatureContainer = D.get(FeatureContainer.class);
        }
        return mFeatureContainer;
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
     * Return {@link FragmentManager}.
     * @return A {@link FragmentManager}. Should not return {@code null}.
     */
    protected FragmentManager getFragmentManager() {
        return getFeatureContainer().getSupportFragmentManager();
    }


    /**
     * Creates the {@link FeatureScope} for this {@link DependencyScopeOwner}. The default
     * implemention attempts to instantiate a concrete {@link FeatureScope} implementation generated
     * by annotation processor. // TODO : REALLY ?
     *
     * @return A {@link FeatureScope}. May not be {@code null}.
     */
    @SuppressWarnings("unchecked")
    protected FeatureScope createDependencyScope() {
        final String scopeClassName = getClass().getName() + SUFFIX_DEPENDENCY_SCOPE;

        try {
            final Class<? extends FeatureScope> scopeClass = (Class<? extends FeatureScope>)Class.forName(scopeClassName);
            FeatureScope scope = scopeClass.newInstance();
            scope.setOwner(this);
        } catch (Exception e) {
            L.e(this, "createDependencyScope", "Failed to create an instance of: " + scopeClassName);
        }
        return null;
    }

    @Override
    public boolean activateView(final View view) {
        return activateView(view, getFeatureContainer());
    }

    /**
     * A {@link Feature} implementation can use this method to activate i.e. to make visible
     * the given {@link View}.
     *
     * @param view A {@link View} to be activated. May not be {@link null}.
     * @param transitionManager The {@link FeatureTransitionManager} to be used.
     * @return A {@code boolean} for the activated {@link View} was actually activated.
     */
    @SuppressWarnings("unchecked")
    protected boolean activateView(final View view, final FeatureTransitionManager transitionManager) {

        if (!transitionManager.canShowView(view)) {
            return false;
        }

        final Class<? extends View> viewClass = getClass(view);
        getScope().cache(viewClass, view);

        if (view instanceof FeatureFragment) {
            final FeatureFragment fragment = (FeatureFragment) view;
            fragment.setFeature(this);
            transitionManager.showFeatureFragment(this, fragment, fragment.getTag());
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
    protected <T extends View> T activateView(final Class<? extends View> viewClass) {
        return activateView(viewClass, getFeatureContainer());
    }

    /**
     * Activates the {@link View} specified by the given class.
     * @param viewClass A {@link Class} specifying the {@link View} to be activated.
     * @param transitionManager The {@link FeatureTransitionManager} to be used.
     * @return The activated {@link View}.
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T activateView(final Class<? extends View> viewClass, final FeatureTransitionManager transitionManager) {
        final View view = get(viewClass);
        if (activateView(view, transitionManager)) {
            return (T)view;
        } else {
            return null;
        }
    }

    @Override
    public void clearBackStack() {
        final FragmentManager manager = getFeatureContainer().getSupportFragmentManager();

        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        mBackStackSize = 0;
    }

    @Override
    @SuppressWarnings("uncheckked")
    public void goBack() {
        goBack(getFeatureContainer());
    }

    @Override
    public void goBack(final FeatureTransitionManager transitionManager) {
        final FragmentManager fragmentManager = getFeatureContainer().getSupportFragmentManager();
        int index = fragmentManager.getBackStackEntryCount() - 1;

        while (index >= 0) {
            final FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(index);
            final String tag = entry.getName();
            final FeatureFragment fragment = (FeatureFragment) fragmentManager.findFragmentByTag(tag);

            if (isActiveView(fragment)) {
                fragmentManager.popBackStackImmediate();
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
        final FragmentManager fragmentManager = getFeatureContainer().getSupportFragmentManager();
        final int count = fragmentManager.getBackStackEntryCount();
        return (count > 1);
    }

    @Override
    public final void restart() {
        super.restart();
    }

    @Override
    @CallSuper
    public final void start(final Params params) {
        super.start(params);
        Dependency.activateScope(this);
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

        Dependency.deactivateScope(this);
        mFeatureManager.onFeatureDestroyed(this);
        clearBackStack();
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
