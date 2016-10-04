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
package com.robopupu.feature.main.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.robopupu.R;
import com.robopupu.api.app.AppConfig;
import com.robopupu.api.feature.FeatureContainer;
import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.api.mvp.PluginCompatActivity;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.app.RobopupuAppScope;
import com.robopupu.app.view.DrawerLayoutContainer;
import com.robopupu.feature.main.MainFeature;
import com.robopupu.feature.main.MainFeatureScope;
import com.robopupu.feature.main.presenter.MainPresenter;

@Plugin
public class MainActivity extends PluginCompatActivity<MainPresenter>
        implements MainView, NavigationView.OnNavigationItemSelectedListener,
        DrawerLayoutContainer {

    private DrawerLayout drawerLayout;

    @Plug(RobopupuAppScope.class) MainFeature feature;
    @Plug(MainFeatureScope.class) MainPresenter presenter;
    @Plug PluginFeatureManager featureManager;

    @Override
    public @IdRes int getContainerViewId() {
        return R.id.frame_layout_fragment_container;
    }

    @Override
    public MainPresenter getPresenter() {
        return presenter;
    }

    @Override
    public FeatureContainer getMainFeatureContainer() {
        return this;
    }

    @Override
    protected void onCreate(final Bundle inState) {
        super.onCreate(inState);

        setContentView(R.layout.activity_main);
        drawerLayout = getView(R.id.drawer_layout_navigation);
        final NavigationView navigationView = getView(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Demo about using AppConfig

        if (!AppConfig.isEnabled(R.integer.config_feature_multiple_views)) {
            final Menu menu = navigationView.getMenu();
            menu.findItem(R.id.navigation_multiple_views).setVisible(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!feature.isStarted()) {
            featureManager.startFeature(this, feature);
        }
    }

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_navigation);

        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                presenter.onBackPressed();
            }
        } else {
            presenter.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        final boolean wasHandled = presenter.onNavigationItemSelected(id);
        final DrawerLayout drawer = getView(R.id.drawer_layout_navigation);
        drawer.closeDrawer(GravityCompat.START);
        return wasHandled;
    }

    @Override
    public void updateForToolbar(final Toolbar toolbar) {
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void openNavigationDrawer() {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
