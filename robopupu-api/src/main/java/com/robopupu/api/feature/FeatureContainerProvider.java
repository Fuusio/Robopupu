package com.robopupu.api.feature;

import android.support.annotation.IdRes;
import android.view.ViewGroup;

import com.robopupu.api.mvp.View;

import java.util.List;

/**
 * {@link FeatureContainerProvider} in an interface for an {@code Activity} that provides one
 * of more {@link FeatureContainer}s.
 */
public interface FeatureContainerProvider extends FeatureContainer {

    List<FeatureContainer> getFeatureContainers();

    /**
     * Shows the given {@link FeatureView}. If parameter {@code fragmentTag} is given {@code null}
     * value, implementation of this method should use the {@link View#getViewTag()} method
     * to obtain the tag.
     *
     * @param featureView A {@link FeatureView}. May not be {@code null}.
     * @param containerViewId The layout ID of the container {@link ViewGroup}.
     * @param addToBackStack A {@code boolean} value specifying if the {@link FeatureView} is added
     *                       to back stack.
     * @param fragmentTag A tag for the {@code Fragment}. May be {@code null}.
     */
    void showView(FeatureView featureView, @IdRes int containerViewId, boolean addToBackStack, String fragmentTag);
}
