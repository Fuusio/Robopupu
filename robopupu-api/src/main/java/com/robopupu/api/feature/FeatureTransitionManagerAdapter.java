package com.robopupu.api.feature;

/**
 * {@link FeatureTransitionManagerAdapter} is an adapter for implementing
 * {@link FeatureTransitionManager}.
 */
public abstract class FeatureTransitionManagerAdapter implements FeatureTransitionManager {

    protected final int mEnterAnim;
    protected final int mExitAnim;
    protected final int mPopEnterAnim;
    protected final int mPopExitAnim;

    public FeatureTransitionManagerAdapter(final int enterAnim, final int exitAnim) {
        this(enterAnim, exitAnim, enterAnim, exitAnim);
    }

    public FeatureTransitionManagerAdapter(final int enterAnim, final int exitAnim, final int popEnterAnim, final int popExitAnim) {
        mEnterAnim = enterAnim;
        mExitAnim = exitAnim;
        mPopEnterAnim = popEnterAnim;
        mPopExitAnim = popExitAnim;
    }

    @Override
    public boolean canShowView(final FeatureView view) {
        return true;
    }

    @Override
    public void showView(FeatureView featureView, boolean addToBackStack, String fragmentTag) {
        // Do nothing
    }

    @Override
    public void removeView(FeatureView featureView, boolean addedToBackStack, String fragmentTag) {
        // Do nothing
    }
}
