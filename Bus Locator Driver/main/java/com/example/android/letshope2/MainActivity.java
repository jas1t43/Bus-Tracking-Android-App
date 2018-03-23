package com.example.android.letshope2;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends Activity {
    GPSTracker gps;
    Double latitude, longitude;
    String name, lat, lon;
    Button b;
    private int mInterval = 20000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    EditText busn,routen,drvn,contn,srcn,dstn;
    String bn,rn,dn,cn,sn,dsn;
    static int count=0;
    //ToggleButton toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
         count=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        busn=(EditText)findViewById(R.id.bus_no);
        routen=(EditText)findViewById(R.id.editText2);
        drvn=(EditText)findViewById(R.id.d_name);
        contn=(EditText)findViewById(R.id.contact_no);
        srcn=(EditText)findViewById(R.id.src);
        dstn=(EditText)findViewById(R.id.dst);
        b=(Button)findViewById(R.id.b1);
        // toggle = (ToggleButton) findViewById(R.id.toggleButton);
        mHandler = new Handler();
        if (networkInfo != null && networkInfo.isConnected()) {
            // textView.setVisibility(View.INVISIBLE);
        } else {
             b.setEnabled(false);
            // b2.setEnabled(false);
            //   btnShowLocation.setEnabled(false);
        }
    }


    Runnable mStatusChecker = new Runnable() {
        //int i=0;
        @Override
        public void run() {
            try {

               chck_location();
                //updateStatus(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

 public  void startRepeatingTask(View view) {
        mStatusChecker.run();
    }

  public   void stopRepeatingTask(View view) {
        mHandler.removeCallbacks(mStatusChecker);
    }


    public void chck_location() {

        if(count==0)
        {
            if(busn.getText().toString().equals("")||routen.getText().toString().equals("")||drvn.getText().toString().equals("")||contn.getText().toString().equals("")||srcn.getText().toString().equals("")||dstn.getText().toString().equals(""))
            {
                Toast.makeText(getApplicationContext(), "Fill the required information first", Toast.LENGTH_LONG).show();
            }
            else
            {
                bn=busn.getText().toString();
                rn=routen.getText().toString();
                dn=drvn.getText().toString();
                cn=contn.getText().toString();
                dsn=dstn.getText().toString();
                sn=srcn.getText().toString();
                busn.setEnabled(false);
                routen.setEnabled(false);
                drvn.setEnabled(false);
                contn.setEnabled(false);
                srcn.setEnabled(false);
                dstn.setEnabled(false);
                count++;
            }
        }
     else {


            gps = new GPSTracker(MainActivity.this);

            // check if GPS enabled
            if (gps.canGetLocation()) {

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                // \n is for new line
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                save_info();
            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();

            }
        }
    }

    public void save_info() {
        lat = Double.toString(latitude);
        lon = Double.toString(longitude);
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(dn, lat, lon,bn,rn,sn,dsn,cn);

       // finish();
    }


    class BackgroundTask extends AsyncTask<String, Void, String> {
        String add_info_url;

        @Override
        protected void onPreExecute() {
            add_info_url = "http://bustracker.ga/add_info1.php";
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... args) {
            String name, lat, lon,bn1,rn1,sn1,dsn1,cn1;
            name = args[0];
            lat = args[1];
            lon = args[2];
            bn1 = args[3];
            rn1 = args[4];
            sn1 = args[5];
            dsn1 = args[6];
            cn1 = args[7];
            try {
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("driver_name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8") + "&" +
                        URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(lon, "UTF-8")+ "&" +
                        URLEncoder.encode("bus_no", "UTF-8") + "=" + URLEncoder.encode(bn1, "UTF-8") + "&" +
                        URLEncoder.encode("route_no", "UTF-8") + "=" + URLEncoder.encode(rn1, "UTF-8") + "&" +
                        URLEncoder.encode("source", "UTF-8") + "=" + URLEncoder.encode(sn1, "UTF-8") + "&" +
                        URLEncoder.encode("destination", "UTF-8") + "=" + URLEncoder.encode(dsn1, "UTF-8")+ "&"+
                URLEncoder.encode("contact_no", "UTF-8") + "=" + URLEncoder.encode(cn1, "UTF-8") ;

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "one row of data inserted";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}


