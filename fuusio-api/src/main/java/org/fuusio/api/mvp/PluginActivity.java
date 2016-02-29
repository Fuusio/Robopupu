package org.fuusio.api.mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.fuusio.api.feature.FeatureContainer;
import org.fuusio.api.feature.FeatureContainerActivity;
import org.fuusio.api.feature.FeatureDialogFragment;
import org.fuusio.api.plugin.PluginBus;
import org.fuusio.api.plugin.PluginComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link PluginActivity} extends {@link ViewActivity} to provide an abstract base class for
 * implementing {@code Activities} that utilise Fuusio Plugin library.
 */
public abstract class PluginActivity<T_Presenter extends Presenter>
        extends ViewActivity<T_Presenter> implements FeatureContainer, FeatureContainerActivity, PluginComponent {

    private final List<FeatureContainer> mFeatureContainers;

    protected PluginActivity() {
        mFeatureContainers = new ArrayList<>();
        mFeatureContainers.add(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public List<FeatureContainer> getFeatureContainers() {
        return mFeatureContainers;
    }

    /**
     * Tests if the given {@link View} can be shown. The default implementation of this method just
     * checks that this {@link ViewActivity} has not been moved to background.
     *
     * @param view A {@link View}.
     * @return A {@code boolean}.
     */
    @Override
    public boolean canShowView(final View view) {
        return (view != null && !mState.isMovedToBackground());
    }

    @Override
    public void showDialogFragment(final FeatureDialogFragment fragment, final boolean addToBackStack, final String fragmentTag) {
        final String tag = (fragmentTag != null) ? fragmentTag : fragment.getViewTag();
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragment, tag);

        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onStart() {
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
