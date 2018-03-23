package com.example.android.buslocatoruser;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;






    RetrieveInfo retrieveInfo;
    JSONObject jsonObject;
    JSONArray jsonArray;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    GoogleMap mMap;
    private static final double
            DITU_Dehradun_LAT = 30.3986,
            DITU_Dehradun_LNG = 78.0748;
    private Marker marker;
    private MarkerOptions opt =new MarkerOptions();
    private ArrayList<LatLng> latln=new ArrayList<>();

    private int mInterval = 12000; // 12 seconds by default, can be changed later
    private Handler mHandler;
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                placeMarker();
                //updateStatus(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };
    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRepeatingTask();
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRepeatingTask();
    }


    public void placeMarker()
    {
        jsonArray=new JSONArray();
        retrieveInfo.getJSON();
        //retrieveInfo.parseJSON();
        if (retrieveInfo.json_string == null)
        {
            Toast.makeText(getApplicationContext(), "Searching for Buses..", Toast.LENGTH_LONG).show();
        }
        else
        {
         /*Intent intent = new Intent(this, DisplayListView.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);*/
            // Toast.makeText(getApplicationContext(), "JSON got", Toast.LENGTH_LONG).show();
            try {
                jsonObject=new JSONObject(retrieveInfo.json_string);
                jsonArray=jsonObject.getJSONArray("server_response");
                int count=0;
                String name,bus,rout,source,destination ;
                Double lat,lon;
                Double lat1,lon1;
                mMap.clear();
                latln.clear();

                while(count<jsonArray.length())
                {
                    JSONObject JO=jsonArray.getJSONObject(count);
                    //name=JO.getString("driver_name");
                    lat=JO.getDouble("lat");
                    lon=JO.getDouble("lon");
                    bus=JO.getString("bus_no");
                    rout=JO.getString("route_no");
                    source=JO.getString("source");
                    destination=JO.getString("destination");
                    final String s=source;
                    final String d=destination;
                    final String r=rout;

                    // lat1=Double.parseDouble(lat);
                    //lon1=Double.parseDouble(lon);
                    latln.add(new LatLng(lat,lon));
                    for(LatLng point:latln)
                    {
                        opt.position(point);
                        opt.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_marker2));
                        mMap.addMarker(opt);
                        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                            @Override
                            public View getInfoWindow(Marker opt) {
                                return null;
                            }

                            @Override
                            public View getInfoContents(Marker marker) {
                                View v = getLayoutInflater().inflate(R.layout.info_window, null);
                                TextView route = (TextView) v.findViewById(R.id.tv_Route);
                                TextView destin = (TextView) v.findViewById(R.id.tv_dest);
                                TextView sour=(TextView)v.findViewById(R.id.tv_scr);
                                TextView capacity = (TextView) v.findViewById(R.id.tv_Capa);
                                route.setText(r);
                                destin.setText(d);
                                sour.setText(s);
                                capacity.setText("Capacity : 50");
                                return v;
                            }
                        });
                    }
                    count++;
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (id)
        {
            case R.id.mapTypeNone:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;

            case R.id.mapTypeNormal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeHybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.mapTypeTerrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeSatellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
















    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        navigationView=(NavigationView)findViewById(R.id.navigationview);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
       // getSupportActionBar().setTitle("BUS LOCATOR MAP");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map_home_nav:


                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.aboutus_nav:

                        Intent intent = new Intent(MainActivity.this, AboutUs.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.bus_info_nav:
                        Intent intent1 = new Intent(MainActivity.this, BusInfo.class);
                        startActivity(intent1);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });





        retrieveInfo=new RetrieveInfo(MainActivity.this);
        if(servicesOK())
        {
           // setContentView(R.layout.activity_map);
            if (initMap())
            {
                Toast.makeText(this, "ready to map!", Toast.LENGTH_SHORT).show();
                gotoLocation(DITU_Dehradun_LAT, DITU_Dehradun_LNG, 10);
                mMap.setMyLocationEnabled(true);
                mHandler = new Handler();
                startRepeatingTask();
            }
            else
            {
                Toast.makeText(this, "map not connected!", Toast.LENGTH_SHORT).show();
            }
            if (this.getIntent().getExtras() != null)
            {
                Bundle b = getIntent().getExtras();
                Double mark_lat=b.getDouble("lat");
                Double mark_long=b.getDouble("long");
                //latln.add(new LatLng(mark_lat,mark_long));
                //   latln.add(new LatLng(12.5148,56.4510));
                // latln.add(new LatLng(12.5148,56.5769));
                //latln.add(new LatLng(12.5648,56.4569));
                /*try{MarkerOptions mark =new MarkerOptions()
                        .position(new LatLng(mark_lat, mark_long))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_marker2));
                    mMap.addMarker(mark);
                    gotoLocation(mark_lat,mark_long,6);}
                catch (Exception e)
                {
                    Toast.makeText(this,"Exception Generated", Toast.LENGTH_SHORT).show();
                }*/
                for(LatLng point:latln)
                {
                    opt.position(point);
                    opt.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_marker2));
                    mMap.addMarker(opt);
                }
                gotoLocation(mark_lat,mark_long,6);
            }
        }
        else
        {
            setContentView(R.layout.activity_main);
        }


























    }

    public boolean servicesOK()
    {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS)
        {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable))
        {
            Dialog dialog =
                    GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to mapping services", Toast.LENGTH_LONG).show();
        }
        return false;

    }


    private boolean initMap()
    {
        if (mMap==null)
        {
            SupportMapFragment mapFragment=
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap=mapFragment.getMap();
        }
        return (mMap!=null);
    }
    private void gotoLocation(double lat,double lng,float zoom)
    {
        LatLng latLng = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);
    }


    private void hideSoftKeyboard(View v)
    {
        InputMethodManager imm= (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public void geoLocate(View v)throws IOException
    {
        hideSoftKeyboard(v);
        TextView tv = (TextView) findViewById(R.id.editText1);
        String searchString = tv.getText().toString();
        Toast.makeText(this,"Searching for:"+searchString,Toast.LENGTH_SHORT).show();
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(searchString, 1);
        if(list.size() > 0 )
        {
            Address add=(Address)list.get(0);
            String locality = add.getPostalCode();
            //String lock = add.
            if(locality!=null)
            {
                Toast.makeText(this, "Found", Toast.LENGTH_SHORT).show();
            }
            double lat=add.getLatitude();
            double lng=add.getLongitude();
            gotoLocation(lat,lng,10);
            if(marker!=null)
            {
                marker.remove();
            }
            //var myLatLng = {lat: -25.363, lng: 131.044};
            MarkerOptions options =new MarkerOptions()
                    .title(locality)
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            marker=mMap.addMarker(options);
        }
    }





    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
