package com.robopupu.app.view;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.robopupu.R;
import com.squareup.picasso.Picasso;

import org.fuusio.api.feature.FeatureFragment;
import org.fuusio.api.feature.FeaturePresenter;

/**
 * {@link CoordinatorLayoutFragment} provides an abstract base class for implementing
 * {@link FeatureFragment}s that use {@link android.support.design.widget.CoordinatorLayout}.
 */
public abstract class CoordinatorLayoutFragment<T_Presenter extends FeaturePresenter>
        extends FeatureFragment<T_Presenter> {

    protected final @StringRes int mTitleResId;

    protected ActionBar mActionBar;
    protected AppBarLayout mAppBarLayout;
    protected FloatingActionButton mFab;
    protected Toolbar mToolbar;

    protected CoordinatorLayoutFragment(@StringRes final int titleResId) {
        mTitleResId = titleResId;
    }

    @Override
    protected void createBindings() {
        super.createBindings();

        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        mAppBarLayout = getView(R.id.app_bar_layout);
        mToolbar = getView(R.id.toolbar);

        activity.setSupportActionBar(mToolbar);

        mActionBar = activity.getSupportActionBar();

        assert(mActionBar != null);

        mActionBar.setTitle(mTitleResId);
        mActionBar.setDisplayShowHomeEnabled(true);

        mFab = getView(R.id.fab);

        if (mFab != null) {
            setupFabAction(mFab);
        }

        final ImageView imageView = getView(R.id.image_view_backdrop);
        setupBackdrop(imageView);
    }

    protected void setupFabAction(final FloatingActionButton fab) {
        // Do nothing by default

        /*mFab.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(final android.view.View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            */
    }

    protected void setupBackdrop(final ImageView backdropImageView) {
        Picasso.with(getActivity()).load(R.drawable.img_backdrop).into(backdropImageView);
    }

    @Override
    public void onActivityCreated(final Bundle inState) {
        super.onActivityCreated(inState);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        if (activity instanceof DrawerLayoutContainer) {
            final DrawerLayoutContainer container = (DrawerLayoutContainer)activity;
            container.updateForToolbar(mToolbar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                getActivity().onBackPressed();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
