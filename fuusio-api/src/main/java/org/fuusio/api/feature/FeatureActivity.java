package org.fuusio.api.feature;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependencyMap;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.dependency.Scopeable;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.mvp.View;
import org.fuusio.api.mvp.ViewActivity;
import org.fuusio.api.util.Params;

import java.util.HashMap;

/**
 * {@link FeatureActivity} is an abstract base class for implementing a {@link ViewActivity} that uses
 * a {@link Feature} to implement UI flow logic. A {@link Feature} implementation receives the lifecycle
 * events of an {@link Activity} and can control UI flow according to the events.
 *
 * @param <T_Feature> A type parameter for {@link Feature}.
 */
public abstract class FeatureActivity<T_Feature extends Feature, T_Presenter extends Presenter>
        extends ViewActivity<T_Presenter> implements FeatureContainer, Scopeable {

    protected static final String KEY_DEPENDENCY_FEATURE = "Key.Dependency.Feature";

    protected T_Feature mFeature;
    private Bundle mParams;
    private DependencyScope mScope;

    @NonNull
    public final T_Feature getFeature() {
        return mFeature;
    }

    public void setFeature(@NonNull final T_Feature feature) {
        mFeature = feature;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(final Bundle inState) {
        super.onCreate(inState);
        mParams = inState;
        mFeature = createFeature(Params.from(mParams));
        // XXX D.activateScope(mFeature);
    }

    /**
     * Creates the {@link Feature} for this {@link FeatureActivity}. This method can be overridden in
     * concrete implementations of {@link FeatureActivity} if method {@link FeatureActivity} used.
     *
     * @param params A {@link Params} containing the parameters.
     * @return The created {@link Feature}.
     */
    protected abstract T_Feature createFeature(final Params params);

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
        return (T) D.get(mScope, dependencyType, dependant);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Params params = Params.from(mParams);

        if (mFeature == null) {
            mFeature = createFeature(params);
        }
        mFeature.start(params);
    }

    @Override
    protected void onSaveDependencies(final DependencyMap dependencies) {
        super.onSaveDependencies(dependencies);
        dependencies.put(KEY_DEPENDENCY_FEATURE, mFeature);
    }

    @Override
    protected void onRestoreDependencies(final DependencyMap dependencies) {
        super.onRestoreDependencies(dependencies);
        mFeature = dependencies.removeDependency(KEY_DEPENDENCY_FEATURE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mFeature.restart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFeature.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFeature.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFeature.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFeature.destroy();
    }

    /**
     * Tests if the given {@link View} can be shown. The default implementation of this method just
     * checks that this {@link ViewActivity} has not been moved to background.
     *
     * @param view A {@link View}.
     * @return A {@code boolean}.
     */
    @Override
    public boolean canShowView(final View view) {
        return (view != null && !mState.isMovedToBackground());
    }
}
