package com.robopupu.feature.main.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.fuusio.api.feature.Feature;
import org.fuusio.api.feature.FeatureContainer;
import org.fuusio.api.feature.FeatureFragment;
import org.fuusio.api.mvp.PluginActivity;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

import com.robopupu.R;
import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.app.DrawerLayoutContainer;
import com.robopupu.feature.main.MainFeature;
import com.robopupu.feature.main.MainFeatureScope;
import com.robopupu.feature.main.presenter.MainPresenter;

@Plugin
public class MainActivity extends PluginActivity<MainPresenter>
        implements MainView, NavigationView.OnNavigationItemSelectedListener,
        FeatureContainer, DrawerLayoutContainer {

    private DrawerLayout mDrawerLayout;

    @Plug(MainFeatureScope.class) MainFeature mFeature;
    @Plug(MainFeatureScope.class) MainPresenter mPresenter;
    @Plug PluginFeatureManager mFeatureManager;

    @Override
    protected MainPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(final Bundle inState) {
        super.onCreate(inState);

        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_navigation);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFeatureManager.startFeature(mFeature);
    }

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_navigation);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mPresenter.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_navigation);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean canShowView(final View view) {
        return true;
    }

    @Override
    public void showFeatureFragment(final Feature feature, final FeatureFragment fragment, final String fragmentTag) {
        final String tag = (fragmentTag != null) ? fragmentTag : fragment.getFeatureTag();
        final FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.frame_layout_fragment_container, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void updateForToolbar(final Toolbar toolbar) {
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void showExitConfirmDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.feature_main_dialog_title_exit_confirmation);
        alertDialog.setMessage(getString(R.string.feature_main_dialog_prompt_exit_app));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                        mPresenter.onExitAppSelected();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int which) {
                        // Just dismiss the dialog
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
