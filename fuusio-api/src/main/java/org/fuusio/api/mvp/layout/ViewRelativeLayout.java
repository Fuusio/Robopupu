package org.fuusio.api.mvp.layout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import org.fuusio.api.binding.AdapterViewBinding;
import org.fuusio.api.binding.ViewBinder;
import org.fuusio.api.binding.ViewBinding;
import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependenciesCache;
import org.fuusio.api.dependency.DependencyMap;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.DependencyScopeOwner;
import org.fuusio.api.dependency.Scopeable;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.mvp.ViewState;

/**
 * {@link ViewRelativeLayout} extends {@link RelativeLayout} to implement {@link View}.
 */
public abstract class ViewRelativeLayout<T_Presenter extends Presenter> extends RelativeLayout
        implements View, Scopeable {

    private final ViewBinder mBinder;
    private final ViewState mState;

    private DependencyScope mScope;

    public ViewRelativeLayout(final Context context) {
        this(context, null);
    }

    public ViewRelativeLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewRelativeLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBinder = new ViewBinder((Activity)context);
        mState = new ViewState(this);
    }

    /**
     * Gets the {@link Presenter} assigned for this {@link ViewRelativeLayout}.
     *
     * @return A {@link Presenter}.
     */
    protected abstract T_Presenter getPresenter();

    /**
     * Resolves the {@link Presenter} assigned for this {@link ViewRelativeLayout}.
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

    public void start() {
        mState.onStart();
        createBindings();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewStart(this);
        }
    }

    /**
     * Invoked to bind {@link ViewBinding}s to {@link View}s. This method has to be overridden in
     * classes extended from {@link ViewRelativeLayout}.
     */
    protected void createBindings() {
        // Do nothing by default
    }

    public void resume() {
        mState.onResume();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewResume(this);
        }
    }

    public void stop() {
        mState.onStop();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewStop(this);
        }
    }

    public void pause() {
        mState.onPause();

        final T_Presenter presenter = resolvePresenter();
        if (presenter != null) {
            presenter.onViewPause(this);
        }
    }

    public void destroy() {
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

    public void saveInstanceState(final Bundle outState) {
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
            cache.saveDependencyScope(owner, owner.getScope());
        }
    }

    public void restoreInstanceState(final Bundle inState) {

        final DependenciesCache cache = D.get(DependenciesCache.class);

        if (this instanceof DependencyScopeOwner) {
            final DependencyScopeOwner owner = (DependencyScopeOwner) this;

            // DependencyScope is automatically restored and activated

            if (cache.containsDependencyScope(owner)) {
                final DependencyScope scope = cache.removeDependencyScope(owner);
                D.activateScope(owner, scope);
            } else {
                D.activateScope(owner);
            }
        }

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

    /**
     * This method can be overridden to save state of this {@link ViewRelativeLayout} to the given
     * {@link Bundle}.
     * @param outState A {@link Bundle}.
     */
    protected void onSaveState(final Bundle outState) {
        // By default do nothing
    }

    /**
     * This method can be overridden to restore state of this {@link ViewRelativeLayout} from the given
     * {@link Bundle}.
     * @param inState A {@link Bundle}.
     */
    protected void onRestoreState(final Bundle inState) {
        // By default do nothing
    }

    /**
     * This method can be overridden to save dependencies after the {@link ViewRelativeLayout} is
     * restored, for instance, after recreating it.
     *
     * @param dependencies A {@link DependencyMap} for saving the dependencies.
     */
    protected void onSaveDependencies(final DependencyMap dependencies) {
        // By default do nothing
    }

    /**
     * This method can be overridden to restore dependencies after the {@link ViewRelativeLayout} is
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
    public <T extends android.view.View> T getView(final int viewId) {
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
    public <T extends ViewBinding<?>> T bind(final int viewId) {
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
    public <T extends android.view.View> T bind(final int viewId, final ViewBinding<T> binding) {
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
    public AdapterView bind(final int viewId, final AdapterViewBinding<?> binding, final AdapterViewBinding.Adapter<?> adapter) {
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Class<?> dependencyType) {
        return (T)D.get(mScope, dependencyType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Class<?> dependencyType, final Object dependant) {
        return (T)D.get(mScope, dependencyType, dependant);
    }

}
