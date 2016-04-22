package com.robopupu.api.feature;

import com.robopupu.api.dependency.Scopeable;
import com.robopupu.api.mvp.View;

/**
 * {@link FeatureView} extends {@link View} to provide a set of {@link Feature} specific
 * framework methods.
 */
public interface FeatureView extends View, Scopeable {

    /**
     * Sets the {@link Feature} that owns this
     * @param feature A {@link Feature}.
     */
    void setFeature(Feature feature);

    /**
     * Tests if this  {@link FeatureView} is a dialog, e.g. {@code DialogFragment}.
     * @return A {@code boolean} value.
     */
    boolean isDialog();
}
