package com.example.roopan.stuconnect;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.security.Permission;

public class AuthNewsfeed extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    static BottomNavigationView bottomNavigationView;
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_newsfeed);

        checkPermission();

        Toolbar toolbar = (Toolbar) findViewById(R.id.authnewstool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        googleApiClient = new GoogleApiClient.Builder(AuthNewsfeed.this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();

        startService(new Intent(this,LocService.class));

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_home:
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.frame_content, Newsfeed.newInstance());
                        fragmentTransaction.commit();
                        break;
                    case R.id.menu_friends:

                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.frame_content, Friends.newInstance());
                        fragmentTransaction.commit();
                        break;
                    case R.id.menu_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,Profile.newInstance()).commit();
                        break;
                    case R.id.menu_signout:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,LogOut.newInstance()).commit();
                        break;
                }
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.frame_content,Newsfeed.newInstance()).commit();
    }

    private void checkPermission() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2)
        {
            if (grantResults[grantResults.length-1] == PackageManager.PERMISSION_GRANTED && grantResults[grantResults.length-2] == PackageManager.PERMISSION_GRANTED )
            {

            }
            else {
                checkPermission();
            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
