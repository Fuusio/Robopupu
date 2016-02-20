package org.fuusio.api.feature;

import org.fuusio.api.dependency.Scopeable;
import org.fuusio.api.mvp.Presenter;

/**
 * {@link FeaturePresenter} ...
 */
public interface FeaturePresenter<T_View extends FeatureView, T_Listener extends Presenter.Listener>
        extends Presenter<T_View, T_Listener>, Scopeable {

    /**
     * Gets the {@link Feature} that controls this {@link FeaturePresenter}.
     *
     * @return A {@link Feature}.
     */
    <T extends Feature> T getFeature();

    /**
     * Sets the {@link Feature} that controls this {@link FeaturePresenter}.
     *
     * @param feature A {@link Feature}.
     */
    void setFeature(Feature feature);
}
