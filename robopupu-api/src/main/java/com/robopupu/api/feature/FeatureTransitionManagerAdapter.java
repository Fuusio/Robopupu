package com.robopupu.api.feature;

import com.robopupu.api.mvp.View;

/**
 * {@link FeatureTransitionManagerAdapter} is an adapter for implementing {@link FeatureTransitionManager}.
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
    public boolean canShowView(final View view) {
        return true;
    }

    @Override
    public void showFragment(FeatureFragment fragment, boolean addToBackStack, String fragmentTag) {
        // Do nothing
    }
}
