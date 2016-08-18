package com.robopupu.api.mvp;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.IdRes;
import android.util.Log;

import com.robopupu.api.feature.FeatureContainer;
import com.robopupu.api.feature.FeatureContainerAdapter;
import com.robopupu.api.feature.FeatureContainerProvider;
import com.robopupu.api.feature.FeatureView;
import com.robopupu.api.feature.FeatureViewFragmentDelegate;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.api.plugin.PluginComponent;
import com.robopupu.api.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link PluginActivity} extends {@link ViewCompatActivity} to provide an abstract base class for
 * implementing {@code Activities} that utilise Robopupu.Plugin library.
 */
public abstract class PluginActivity<T_Presenter extends Presenter>
        extends ViewActivity<T_Presenter> implements FeatureContainer, FeatureContainerProvider, PluginComponent {

    private final static String TAG = Utils.tag(PluginActivity.class);

    private final List<FeatureContainer> featureContainers;

    protected PluginActivity() {
        featureContainers = new ArrayList<>();
    }

    protected void createFeatureContainers(final List<FeatureContainer> containers) {
        containers.add(this);
    }

    protected FeatureContainer createFeatureContainer(final @IdRes int containerViewId) {
        return FeatureContainerAdapter.create(this, containerViewId);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public List<FeatureContainer> getFeatureContainers() {
        return featureContainers;
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
        final FragmentManager fragmentManager = getFragmentManager();
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

        final FragmentManager manager = getFragmentManager();

        if (manager.findFragmentByTag(tag) == null) {
            final FragmentTransaction transaction = manager.beginTransaction();

            if (featureView instanceof DialogFragment) {
                final DialogFragment dialogFragment = (DialogFragment)featureView;
                transaction.add(dialogFragment, tag);

                if (addToBackStack) {
                    transaction.addToBackStack(tag);
                }
                transaction.commitAllowingStateLoss();
            } else if (featureView instanceof Fragment) {
                final Fragment fragment = (Fragment)featureView;
                transaction.replace(containerViewId, fragment, tag);

                if (addToBackStack) {
                    transaction.addToBackStack(tag);
                }
                transaction.commit();
            } else if (featureView instanceof FeatureViewFragmentDelegate) {
                final Fragment fragment = ((FeatureViewFragmentDelegate)featureView).getFragment();
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
            final FragmentManager manager = getFragmentManager();

            String tag = (fragmentTag != null) ? fragmentTag : featureView.getViewTag();

            if (manager.findFragmentByTag(tag) != null) {
                final FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(fragment);
                transaction.commit();

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
        final FragmentManager manager = getFragmentManager();
        for (final String tag : backStackViews.keySet()) {
            manager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        // manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public String goBack() {
        final FragmentManager fragmentManager = getFragmentManager();
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
        createFeatureContainers(featureContainers);
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
