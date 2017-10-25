package com.samet.offlinedic.pro;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.samet.offlinedic.pro.database.DBHelper;
import com.samet.offlinedic.pro.fragments.DailyPharasesFragment;
import com.samet.offlinedic.pro.fragments.FavoritesFragment;
import com.samet.offlinedic.pro.fragments.HistoryFragment;
import com.samet.offlinedic.pro.fragments.IrregularVerbsFragment;
import com.samet.offlinedic.pro.fragments.PharasalVerbsFragment;
import com.samet.offlinedic.pro.fragments.SearchFragment;
import com.samet.offlinedic.pro.fragments.SettingsFragment;
import com.samet.offlinedic.pro.model.DataHolder;
import com.samet.offlinedic.pro.model.Direction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


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
        verifyStoragePermissions(this);
        readLastTranslationDirection();
        DataHolder.getInstance().dbHelper = new DBHelper(this);
    }

    private void readLastTranslationDirection() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        DataHolder.getInstance().direction = Direction.valueOf(sharedPref.getString(getString(R.string.direction), Direction.EN2TR.getName()));
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

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
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
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_holder, DailyPharasesFragment.newInstance(), getString(R.string.common_pharases)).commit();
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