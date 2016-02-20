package org.fuusio.api.feature;

import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.mvp.View;

public interface FeatureView<T_Presenter extends Presenter> extends View<T_Presenter> {

    /**
     * Gets the {@link Feature} that controls this {@link FeatureView}.
     *
     * @return A {@link Feature}.
     */
    <T extends Feature> T getFeature();

    /**
     * Sets the {@link Feature} that controls this {@link FeatureView}.
     *
     * @param feature A {@link Feature}.
     */
    void setFeature(Feature feature);
}
