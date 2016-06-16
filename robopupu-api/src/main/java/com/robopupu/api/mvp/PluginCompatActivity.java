package com.robopupu.api.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.robopupu.api.feature.FeatureContainer;
import com.robopupu.api.feature.FeatureContainerAdapter;
import com.robopupu.api.feature.FeatureContainerProvider;
import com.robopupu.api.feature.FeatureView;
import com.robopupu.api.feature.FeatureViewCompatFragmentDelegate;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.api.plugin.PluginComponent;
import com.robopupu.api.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link PluginCompatActivity} extends {@link ViewCompatActivity} to provide an abstract base class for
 * implementing {@code Activities} that utilise Robopupu.Plugin library.
 */
public abstract class PluginCompatActivity<T_Presenter extends Presenter>
        extends ViewCompatActivity<T_Presenter> implements FeatureContainer, FeatureContainerProvider, PluginComponent {

    private final static String TAG = Utils.tag(PluginCompatActivity.class);

    private final List<FeatureContainer> mFeatureContainers;

    protected PluginCompatActivity() {
        mFeatureContainers = new ArrayList<>();
    }

    protected void createFeatureContainers(final List<FeatureContainer> featureContainers) {
        featureContainers.add(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public List<FeatureContainer> getFeatureContainers() {
        return mFeatureContainers;
    }

    protected FeatureContainer createFeatureContainer(final @IdRes int containerViewId) {
        return FeatureContainerAdapter.create(this, containerViewId);
    }

    /**
     * Note: This method has to be overridden in extended classes which act as FeatureContainers
     * themselves.
     * @return Dummy container view id -1.
     */
    @Override
    public @IdRes int getContainerViewId() {
        return -1;
    }

    @Override
    public boolean canGoBack() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final int count = fragmentManager.getBackStackEntryCount();
        return (count > 0);
    }

    @Override
    public boolean canShowView(final FeatureView view) {
        return (view != null && !mState.isMovedToBackground());
    }

    @Override
    public void showView(final FeatureView featureView, final boolean addToBackStack, final String fragmentTag) {
        showView(featureView, getContainerViewId(), addToBackStack, fragmentTag);
    }

    @Override
    public void showView(final FeatureView featureView, final @IdRes int containerViewId, final boolean addToBackStack, final String fragmentTag) {
        String tag = fragmentTag;

        if (fragmentTag == null) {
            tag = featureView.getViewTag();
        }

        final FragmentManager manager = getSupportFragmentManager();

        if (manager.findFragmentByTag(tag) == null) {
            final FragmentTransaction transaction = manager.beginTransaction();

            if (featureView instanceof DialogFragment) {
                final DialogFragment dialogFragment = (DialogFragment) featureView;
                transaction.add(dialogFragment, tag);

                if (addToBackStack) {
                    transaction.addToBackStack(tag);
                }
                transaction.commitAllowingStateLoss();
            } else if (featureView instanceof Fragment) {
                final Fragment fragment = (Fragment) featureView;
                transaction.replace(containerViewId, fragment, tag);

                if (addToBackStack) {
                    transaction.addToBackStack(tag);
                }
                transaction.commit();
            } else if (featureView instanceof FeatureViewCompatFragmentDelegate) {
                final Fragment fragment = ((FeatureViewCompatFragmentDelegate)featureView).getFragment();
                transaction.replace(containerViewId, fragment, tag);

                if (addToBackStack) {
                    transaction.addToBackStack(tag);
                }
                transaction.commit();
            }
        } else {
            Log.d(TAG, "showView(...) : A FeatureView with tag '" + tag + "' already exists in backstack");
        }
    }

    @Override
    public void removeView(final FeatureView featureView, final boolean addedToBackstack, final String fragmentTag) {
        if (featureView instanceof DialogFragment) {
            final DialogFragment dialogFragment = (DialogFragment)featureView;
            dialogFragment.dismiss();
        } else if (featureView instanceof Fragment) {
            final Fragment fragment = (Fragment)featureView;
            final FragmentManager manager = getSupportFragmentManager();

            String tag = (fragmentTag != null) ? fragmentTag : featureView.getViewTag();

            if (manager.findFragmentByTag(tag) != null) {
                if (getState().isStarted()) {
                    final FragmentTransaction transaction = manager.beginTransaction();
                    transaction.remove(fragment);
                    transaction.commit();
                }

                if (addedToBackstack) {
                    manager.popBackStack();
                }
            } else {
                Log.d(TAG, "hideView(...) : A FeatureView with tag '" + tag + "' was not found.");
            }
        }
    }


    @Override
    public void clearBackStack(final HashMap<String, FeatureView> backStackViews) {
        final FragmentManager manager = getSupportFragmentManager();

        for (final String tag : backStackViews.keySet()) {
            manager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public String goBack() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final int count = fragmentManager.getBackStackEntryCount();
        String tag = null;

        if (count > 0) {
            final FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(count - 1);
            tag = entry.getName();
            fragmentManager.popBackStack();
        }
        return tag;
    }

    @Override
    protected void onStart() {
        createFeatureContainers(mFeatureContainers);
        PluginBus.plug(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        PluginBus.unplug(this);
    }

    @Override
    public void onPlugged(final PluginBus bus) {
        // Do nothing by default
    }

    @Override
    public void onUnplugged(final PluginBus bus) {
        // Do nothing by default
    }

    @Override
    public void onPluginPlugged(final Object plugin) {
        // Do nothing by default
    }

    @Override
    public void onPluginUnplugged(final Object plugin) {
        // Do nothing by default
    }
}
