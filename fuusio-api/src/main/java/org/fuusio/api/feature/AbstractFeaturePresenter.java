package org.fuusio.api.feature;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.mvp.AbstractPresenter;
import org.fuusio.api.mvp.Presenter;

/**
 * {@link AbstractFeaturePresenter} extends {@link AbstractPresenter} to provide an abstract base class
 * for implementing {@link FeaturePresenter}s that are controlled by {@link Feature}s.
 */
public abstract class AbstractFeaturePresenter<T_View extends FeatureView> extends AbstractPresenter<T_View>
        implements FeaturePresenter {

    private Feature mFeature;
    private DependencyScope mScope;

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Feature> T getFeature() {
        return (T) mFeature;
    }

    @Override
    public void setFeature(final Feature flow) {
        mFeature = flow;
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
        return (T) D.get(mScope, dependencyType, dependant);
    }
}
