package com.robopupu.feature.main.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.fuusio.api.feature.FeatureContainer;
import org.fuusio.api.feature.FeatureDialogFragment;
import org.fuusio.api.feature.FeatureFragment;
import org.fuusio.api.mvp.PluginActivity;
import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

import com.robopupu.R;
import com.robopupu.api.feature.PluginFeatureManager;
import com.robopupu.api.feature.PluginViewContainer;
import com.robopupu.app.RobopupuAppScope;
import com.robopupu.app.view.DrawerLayoutContainer;
import com.robopupu.feature.main.MainFeature;
import com.robopupu.feature.main.MainFeatureScope;
import com.robopupu.feature.main.presenter.MainPresenter;

@Plugin
public class MainActivity extends PluginActivity<MainPresenter>
        implements MainView, NavigationView.OnNavigationItemSelectedListener,
        DrawerLayoutContainer, PluginViewContainer {

    private DrawerLayout mDrawerLayout;

    @Plug(RobopupuAppScope.class) MainFeature mFeature;
    @Plug(MainFeatureScope.class) MainPresenter mPresenter;
    @Plug PluginFeatureManager mFeatureManager;

    @Override
    protected MainPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public FeatureContainer getMainFeatureContainer() {
        return this;
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
        mFeatureManager.startFeature(this, mFeature);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();

        // TODO : To Presenter
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        final boolean wasHandled = mPresenter.onNavigationItemSelected(id);
        final DrawerLayout drawer = getView(R.id.drawer_layout_navigation);
        drawer.closeDrawer(GravityCompat.START);
        return wasHandled;
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
    public void showFragment(final FeatureFragment fragment, final String fragmentTag) {
        final String tag = (fragmentTag != null) ? fragmentTag : fragment.getFragmentTag();
        final FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.frame_layout_fragment_container, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void showDialogFragment(final FeatureDialogFragment fragment, final String fragmentTag) {
        final String tag = (fragmentTag != null) ? fragmentTag : fragment.getFragmentTag();
        final FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
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
        alertDialog.setTitle(R.string.ft_main_dialog_title_exit_confirmation);
        alertDialog.setMessage(getString(R.string.ft_main_dialog_prompt_exit_app));
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
