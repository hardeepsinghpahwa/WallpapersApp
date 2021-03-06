package com.pahwa.hardeep.wallpapers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.pahwa.hardeep.wallpapers.NavigationFrags.Feedback;
import com.pahwa.hardeep.wallpapers.NavigationFrags.Home;
import com.pahwa.hardeep.wallpapers.NavigationFrags.Privacy_Policy;
import com.pahwa.hardeep.wallpapers.NavigationFrags.Rate;
import com.pahwa.hardeep.wallpapers.NavigationFrags.Upload_Images;
import com.pahwa.hardeep.wallpapers.View_Pager_Frags.Categories;
import com.pahwa.hardeep.wallpapers.View_Pager_Frags.Downloaded_Images;
import com.pahwa.hardeep.wallpapers.View_Pager_Frags.Random_Images;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Categories.OnFragmentInteractionListener, Random_Images.OnFragmentInteractionListener, Downloaded_Images.OnFragmentInteractionListener {

    NavigationView navigationView;
    AdView adView;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());

        MobileAds.initialize(this, "ca-app-pub-9643831152040209~5461913154");


        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Log.i("Info", String.valueOf(adRequest.isTestDevice(MainActivity.this)));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!checkWriteExternalPermission()) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }

        getSupportActionBar().setTitle("Explore Wallpapers");

        Fragment fragment = new Home();

        FragmentTransaction f = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        f.replace(R.id.frame, fragment);
        f.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private boolean checkWriteExternalPermission() {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.homefrag) {
            fragment = new Home();
        } else if (id == R.id.ratefrag) {
            fragment = new Rate();
        } else if (id == R.id.uploadmenufrag) {
            fragment = new Upload_Images();
        } else if (id == R.id.feedbackfrag) {
            fragment = new Feedback();
        } else if (id == R.id.privacypolicy) {
            fragment = new Privacy_Policy();
        }


        FragmentTransaction f = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        f.replace(R.id.frame, fragment);
        f.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
