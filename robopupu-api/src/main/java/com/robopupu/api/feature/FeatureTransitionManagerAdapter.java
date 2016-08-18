package com.robopupu.api.feature;

/**
 * {@link FeatureTransitionManagerAdapter} is an adapter for implementing
 * {@link FeatureTransitionManager}.
 */
public abstract class FeatureTransitionManagerAdapter implements FeatureTransitionManager {

    protected final int enterAnim;
    protected final int exitAnim;
    protected final int popEnterAnim;
    protected final int popExitAnim;

    public FeatureTransitionManagerAdapter(final int enterAnim, final int exitAnim) {
        this(enterAnim, exitAnim, enterAnim, exitAnim);
    }

    public FeatureTransitionManagerAdapter(final int enterAnim, final int exitAnim, final int popEnterAnim, final int popExitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        this.popEnterAnim = popEnterAnim;
        this.popExitAnim = popExitAnim;
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
