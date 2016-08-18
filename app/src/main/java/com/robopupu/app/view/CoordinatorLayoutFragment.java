/*
 * Copyright (C) 2016 Marko Salmela, http://robopupu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robopupu.app.view;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.robopupu.R;
import com.robopupu.component.AppManager;
import com.squareup.picasso.Picasso;

import com.robopupu.api.dependency.D;
import com.robopupu.api.feature.FeatureCompatFragment;
import com.robopupu.api.feature.FeaturePresenter;

/**
 * {@link CoordinatorLayoutFragment} provides an abstract base class for implementing
 * {@link FeatureCompatFragment}s that use {@link android.support.design.widget.CoordinatorLayout}.
 */
public abstract class CoordinatorLayoutFragment<T_Presenter extends FeaturePresenter>
        extends FeatureCompatFragment<T_Presenter> {

    protected final @StringRes int titleResId;

    protected ActionBar actionBar;
    protected AppBarLayout appBarLayout;
    protected CoordinatorLayout coordinatorLayout;
    protected FloatingActionButton fab;
    protected Toolbar toolbar;

    protected CoordinatorLayoutFragment(@StringRes final int titleResId) {
        this.titleResId = titleResId;
    }

    @Override
    protected void onCreateBindings() {
        super.onCreateBindings();

        coordinatorLayout = getView(R.id.coordinator_layout);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        appBarLayout = getView(R.id.app_bar_layout);
        toolbar = getView(R.id.toolbar);

        activity.setSupportActionBar(toolbar);

        actionBar = activity.getSupportActionBar();

        assert(actionBar != null);

        actionBar.setTitle(titleResId);
        actionBar.setDisplayShowHomeEnabled(true);

        fab = getView(R.id.fab);

        if (fab != null) {
            setupFabAction(fab);
        }

        //final ImageView imageView = getView(R.id.image_view_backdrop);
        //setupBackdrop(imageView);
    }

    protected void setupFabAction(final FloatingActionButton fab) {
        // Do nothing by default
    }

    protected void setupBackdrop(final ImageView backdropImageView) {
        Picasso.with(getActivity()).load(R.drawable.img_backdrop).into(backdropImageView);
    }

    public void showMessage(final String message) {

        final AppManager appManager = D.get(AppManager.class);

        final Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        final View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(appManager.getColor(R.color.primary));
        final TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(appManager.getColor(R.color.white_text));
        snackbar.show();
    }

    @Override
    public void onActivityCreated(final Bundle inState) {
        super.onActivityCreated(inState);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        if (activity instanceof DrawerLayoutContainer) {
            final DrawerLayoutContainer container = (DrawerLayoutContainer)activity;
            container.updateForToolbar(toolbar);
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
