package com.samet.offlinedic.pro;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.samet.offlinedic.pro.fragments.CommonPharasesFragment;
import com.samet.offlinedic.pro.fragments.FavoritesFragment;
import com.samet.offlinedic.pro.fragments.HistoryFragment;
import com.samet.offlinedic.pro.fragments.IrregularVerbsFragment;
import com.samet.offlinedic.pro.fragments.PharasalVerbsFragment;
import com.samet.offlinedic.pro.fragments.SearchFragment;
import com.samet.offlinedic.pro.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView walletIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        walletIdTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_current_wallet_text_view);
        walletIdTextView.setText("asdasda");
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_holder, SearchFragment.newInstance(), getString(R.string.dictionary)).commit();
                break;
            case R.id.nav_commons:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_holder, CommonPharasesFragment.newInstance(), getString(R.string.common_pharases)).commit();
                break;
            case R.id.nav_favorites:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_holder, FavoritesFragment.newInstance(), getString(R.string.favorites)).commit();
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_holder, HistoryFragment.newInstance(), getString(R.string.history)).commit();
                break;
            case R.id.nav_irregular:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_holder, IrregularVerbsFragment.newInstance(), getString(R.string.irregular_verbs)).commit();
                break;
            case R.id.nav_pharasal:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_holder, PharasalVerbsFragment.newInstance(), getString(R.string.pharasal_verbs)).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_holder, SettingsFragment.newInstance(), getString(R.string.settings)).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}