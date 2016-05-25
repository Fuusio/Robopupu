package com.robopupu.api.feature;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;

/**
 * {@link FeatureContainerAdapter} provides an adapter class for implementing
 * {@link FeatureContainer}s.
 */
public class FeatureContainerAdapter implements FeatureContainer {

    private final int mContainerViewId;
    private final FeatureContainerProvider mProvider;

    public FeatureContainerAdapter(final FeatureContainerProvider provider) {
        this(provider, -1);
    }

    public FeatureContainerAdapter(final FeatureContainerProvider provider, final int containerViewId) {
        mProvider = provider;
        mContainerViewId = containerViewId;
    }

    public static FeatureContainerAdapter create(final FeatureContainerProvider provider, final int containerViewId) {
        return new FeatureContainerAdapter(provider, containerViewId);
    }

    @Override
    public Context getContext() {
        return mProvider.getContext();
    }

    @Override
    public int getContainerViewId() {
        return mContainerViewId;
    }

    @Override
    public Resources getResources() {
        return mProvider.getResources();
    }

    @Override
    public boolean canCommitFragment() {
        return mProvider.canCommitFragment();
    }

    @Override
    public void clearBackStack(final HashMap<String, FeatureView> backStackViews) {
        mProvider.clearBackStack(backStackViews);
    }

    @Override
    public boolean canGoBack() {
        return mProvider.canGoBack();
    }

    @Override
    public String goBack() {
        return mProvider.goBack();
    }

    @Override
    public boolean canShowView(final FeatureView view) {
        return mProvider.canShowView(view);
    }

    @Override
    public void showView(final FeatureView featureView, final boolean addToBackStack, final String fragmentTag) {
        mProvider.showView(featureView, getContainerViewId(), addToBackStack, fragmentTag);
    }

    @Override
    public void removeView(final FeatureView featureView, final boolean addedToBackstack, final String fragmentTag) {
        mProvider.removeView(featureView, addedToBackstack, fragmentTag);
    }
}
