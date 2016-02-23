package org.fuusio.api.feature;

import org.fuusio.api.dependency.D;
import org.fuusio.api.dependency.DependencyScope;
import org.fuusio.api.mvp.AbstractPresenter;
import org.fuusio.api.mvp.View;

/**
 * {@link AbstractFeaturePresenter} extends {@link AbstractPresenter} to provide an abstract base class
 * for implementing {@link FeaturePresenter}s that are controlled by {@link Feature}s.
 */
public abstract class AbstractFeaturePresenter<T_View extends View> extends AbstractPresenter<T_View>
        implements FeaturePresenter {

    private Feature mFeature;
    private DependencyScope mScope;

    @SuppressWarnings("unchecked")
    @Override
    public Feature getFeature() {
        return mFeature;
    }

    @Override
    public void setFeature(final Feature feature) {
        mFeature = feature;
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
