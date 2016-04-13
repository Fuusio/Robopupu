package com.robopupu.api.feature;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.robopupu.api.mvp.View;

/**
 * {@link FeatureContainerAdapter} provides an adapter for implementing {@link FeatureContainer}s.
 * Typically an {@link AppCompatActivity} would implement {@link FeatureContainer} interface directly,
 * but if an {@link AppCompatActivity} implements {@link FeatureContainerActivity} interface, it may
 * provide multiple {@link FeatureContainer}s, one for each {@link ViewGroup} that is used as
 * a container for a {@link FeatureFragment}.
 */
public class FeatureContainerAdapter
        implements FeatureContainer {

    private final AppCompatActivity mActivity;
    private final @IdRes int mContainerViewId;

    private ViewGroup mFragmentContainer;

    public FeatureContainerAdapter(final AppCompatActivity activity, final int containerViewId) {
        mActivity = activity;
        mContainerViewId = containerViewId;
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public int getContainerViewId() {
        return mContainerViewId;
    }

    @Override
    public Resources getResources() {
        return mActivity.getResources();
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Override
    public boolean canCommitFragment() {
        return true;
    }

    @Override
    public boolean canShowView(final View view) {
        return true;
    }

    @Override
    public void showFragment(final FeatureFragment fragment, final boolean addToBackStack, final String fragmentTag) {
        final String tag = (fragmentTag != null) ? fragmentTag : fragment.getViewTag();
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(getContainerViewId(), fragment, tag);

        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public void showDialogFragment(final FeatureDialogFragment dialogFragment, final boolean addToBackStack, final String fragmentTag) {
        final String tag = (fragmentTag != null) ? fragmentTag : dialogFragment.getViewTag();
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(dialogFragment, tag);

        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
    }
}
