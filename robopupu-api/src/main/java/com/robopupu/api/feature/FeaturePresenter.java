package com.robopupu.api.feature;

import com.robopupu.api.dependency.Scopeable;
import com.robopupu.api.mvp.Presenter;

/**
 * {@link FeaturePresenter} extends {@link Presenter} interface for Presenters that used with
 * {@link Feature}s.
 */
public interface FeaturePresenter extends Presenter, Scopeable {

    /**
     * Gets the {@link Feature} that controls this {@link FeaturePresenter}.
     *
     * @return A {@link Feature}.
     */
    Feature getFeature();

    /**
     * Sets the {@link Feature} that controls this {@link FeaturePresenter}.
     *
     * @param feature A {@link Feature}.
     */
    void setFeature(Feature feature);
}
