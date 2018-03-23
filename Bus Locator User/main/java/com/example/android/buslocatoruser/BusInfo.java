package com.example.android.buslocatoruser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class BusInfo extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.bus_info_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map_home_nav: {
                        Intent intent1 = new Intent(BusInfo.this, MainActivity.class);
                        startActivity(intent1);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.aboutus_nav: {
                        Intent intent1 = new Intent(BusInfo.this, AboutUs.class);
                        startActivity(intent1);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.bus_info_nav: {
                        Intent intent1 = new Intent(BusInfo.this, BusInfo.class);
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
    public void showroute1(View view)
    {
        Intent i= new Intent(BusInfo.this,Route1.class);
        startActivity(i);
    }

    public void showroute2(View view)
    {
        Intent i= new Intent(BusInfo.this,Route2.class);
        startActivity(i);
    }

    public void showroute3(View view)
    {
        Intent i= new Intent(BusInfo.this,Route3.class);
        startActivity(i);
    }

    public void showroute4(View view)
    {
        Intent i= new Intent(BusInfo.this,Route4.class);
        startActivity(i);
    }


    public void showroute5(View view)
    {
        Intent i= new Intent(BusInfo.this,Route5.class);
        startActivity(i);
    }


    public void showroute6(View view)
    {
        Intent i= new Intent(BusInfo.this,Route6.class);
        startActivity(i);
    }


    public void showroute7(View view)
    {
        Intent i= new Intent(BusInfo.this,Route7.class);
        startActivity(i);
    }

    public void showroute8(View view)
    {
        Intent i= new Intent(BusInfo.this,Route8.class);
        startActivity(i);
    }



    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
