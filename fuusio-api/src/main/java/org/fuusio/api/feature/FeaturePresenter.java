package org.fuusio.api.feature;

import org.fuusio.api.dependency.Scopeable;
import org.fuusio.api.mvp.Presenter;

/**
 * {@link FeaturePresenter} ...
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
