package com.robopupu.api.feature;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;

/**
 * {@link FeatureContainerAdapter} provides an adapter class for implementing
 * {@link FeatureContainer}s.
 */
public class FeatureContainerAdapter implements FeatureContainer {

    private final int containerViewId;
    private final FeatureContainerProvider provider;

    public FeatureContainerAdapter(final FeatureContainerProvider provider) {
        this(provider, -1);
    }

    public FeatureContainerAdapter(final FeatureContainerProvider provider, final int containerViewId) {
        this.provider = provider;
        this.containerViewId = containerViewId;
    }

    public static FeatureContainerAdapter create(final FeatureContainerProvider provider, final int containerViewId) {
        return new FeatureContainerAdapter(provider, containerViewId);
    }

    @Override
    public Context getContext() {
        return provider.getContext();
    }

    @Override
    public int getContainerViewId() {
        return containerViewId;
    }

    @Override
    public Resources getResources() {
        return provider.getResources();
    }

    @Override
    public boolean canCommitFragment() {
        return provider.canCommitFragment();
    }

    @Override
    public void clearBackStack(final HashMap<String, FeatureView> backStackViews) {
        provider.clearBackStack(backStackViews);
    }

    @Override
    public boolean canGoBack() {
        return provider.canGoBack();
    }

    @Override
    public String goBack() {
        return provider.goBack();
    }

    @Override
    public boolean canShowView(final FeatureView view) {
        return provider.canShowView(view);
    }

    @Override
    public void showView(final FeatureView featureView, final boolean addToBackStack, final String fragmentTag) {
        provider.showView(featureView, getContainerViewId(), addToBackStack, fragmentTag);
    }

    @Override
    public void removeView(final FeatureView featureView, final boolean addedToBackstack, final String fragmentTag) {
        provider.removeView(featureView, addedToBackstack, fragmentTag);
    }
}
