package com.robopupu.app.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

    protected ActionBar mActionBar;
    protected FloatingActionButton mFab;
    protected Toolbar mToolbar;

    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = getView(R.id.toolbar);
            final AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(mToolbar);
            mActionBar = activity.getSupportActionBar();
        }
        return mToolbar;
    }

    @Override
    protected void createBindings() {
        super.createBindings();

        getToolbar();

        mFab = getView(R.id.fab);
        mFab.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(final android.view.View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final ImageView imageView = getView(R.id.image_view_backdrop);
        setupBackdrop(imageView);
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
            container.updateForToolbar(getToolbar());
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
