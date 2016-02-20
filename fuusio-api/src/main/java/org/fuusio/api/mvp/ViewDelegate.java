package org.fuusio.api.mvp;

import android.support.annotation.NonNull;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependenciesCache;
import org.fuusio.api.dependency.DependencyMap;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.DependencyScopeOwner;
import org.fuusio.api.dependency.ScopedObject;

/**
 * {@link ViewDelegate} implements {@link View} and can be as a delegate for UI components without
 * making them to implement {@link View} interface.
 */
public class ViewDelegate<T_Presenter extends Presenter>
        extends ScopedObject implements DependencyScopeOwner, View<T_Presenter> {

    private final Callback<T_Presenter> mDelegatedObject;

    private T_Presenter mPresenter;
    private DependencyScope mScope;
    private ViewState mState;

    /**
     * Constructs a {@link ViewDelegate} for the given delegated {@link Object}
     * @param delegatedObject The delegated {@link Object}. May not be {@code null}.
     */
    public ViewDelegate(@NonNull final Callback<T_Presenter> delegatedObject) {
        mDelegatedObject = delegatedObject;
        mState = new ViewState(this);
    }

    @Override
    public T_Presenter getPresenter() {
        if (mPresenter == null) {

            final DependenciesCache cache = D.get(DependenciesCache.class);
            final DependencyMap dependencies = cache.getDependencies(this);

            if (dependencies != null) {
                mPresenter = dependencies.getDependency(KEY_DEPENDENCY_PRESENTER);
            }

            if (mPresenter == null) {
                mPresenter = getPresenterDependency();
            }
        }
        return mPresenter;
    }

    /**
     * This method has to implemented by concrete implementations of this class.
     *
     * @return A {@link Presenter}.
     */
    protected T_Presenter getPresenterDependency() {
        return mDelegatedObject.getPresenterDependency();
    }

    public void setPresenter(final T_Presenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public ViewState getState() {
        return mState;
    }

    @Override
    public boolean hasFocus() {
        return mDelegatedObject.hasFocus();
    }

    public void saveState() {
        final DependenciesCache cache = D.get(DependenciesCache.class);

        // Save a reference to the Presenter

        final DependencyMap dependencies = cache.getDependencies(this, true);
        dependencies.addDependency(KEY_DEPENDENCY_PRESENTER, mPresenter);
        dependencies.addDependency(KEY_DEPENDENCY_SCOPE, mScope);

        mDelegatedObject.onSaveDependencies(dependencies);

        cache.saveDependencyScope(this, getScope());
    }

    public void restoreState() {

        final DependenciesCache cache = D.get(DependenciesCache.class);

        if (cache.containsDependencyScope(this)) {
            final DependencyScope scope = cache.removeDependencyScope(this);
            D.activateScope(this, scope);
        } else {
            D.activateScope(this);
        }

        final DependencyMap dependencies = cache.getDependencies(this);

        if (dependencies != null) {

            mPresenter = dependencies.getDependency(KEY_DEPENDENCY_PRESENTER);

            final DependencyScope scope = dependencies.getDependency(KEY_DEPENDENCY_SCOPE);

            if (scope != null) {
                mScope = scope;
            }

            mDelegatedObject.onRestoreDependencies(dependencies);
        }
    }

    public void start() {
        mState.onStart();
        mPresenter.onViewStart(this);
    }

    public void resume() {
        mState.onResume();
        mPresenter.onViewResume(this);
    }

    public void stop() {
        mState.onStop();
        mPresenter.onViewStop(this);
    }

    public void pause() {
        mState.onPause();
        mPresenter.onViewPause(this);
    }

    public void destroy() {
        mState.onDestroy();

        final DependenciesCache cache = D.get(DependenciesCache.class);
        cache.removeDependencies(this);
        cache.removeDependencyScope(this);

        mPresenter.onViewDestroy(this);
    }

    @NonNull
    @Override
    public String getDependenciesKey() {
        return mDelegatedObject.getDependenciesKey();
    }

    @Override
    public String getScopeId() {
        return getDependenciesKey();
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
        return (T) D.get(mScope, dependencyType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Class<?> dependencyType, final Object dependant) {
        return (T)D.get(mScope, dependencyType, dependant);
    }

    public interface Callback<T_Presenter> {
        boolean hasFocus();
        String getDependenciesKey();
        T_Presenter getPresenterDependency();
        void onSaveDependencies(DependencyMap map);
        void onRestoreDependencies(DependencyMap map);
    }
}
