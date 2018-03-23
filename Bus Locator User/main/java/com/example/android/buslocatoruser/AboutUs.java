package com.example.android.buslocatoruser;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class AboutUs extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.about_us_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map_home_nav:

                    {
                        Intent intent1 = new Intent(AboutUs.this, MainActivity.class);
                        startActivity(intent1);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.aboutus_nav:

                         {Intent intent1 = new Intent(AboutUs.this, BusInfo.class);
                        startActivity(intent1);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                        }
                    case R.id.bus_info_nav: {
                        Intent intent1 = new Intent(AboutUs.this, BusInfo.class);
                        startActivity(intent1);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();

                        break;
                    }
                }
                return false;
            }
        });

    }


    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();

    }
}
