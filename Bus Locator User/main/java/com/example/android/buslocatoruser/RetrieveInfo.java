package com.example.android.buslocatoruser;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Gurtej Singh on 21-Apr-16.
 */
public class RetrieveInfo extends Activity
{
    String JSON_STRING;
    String json_string;
    private final Context rContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.retrieve_info)

    }
    public RetrieveInfo(Context context)
    {
        this.rContext=context;

        BackgroundTask backgroundTask=new BackgroundTask();
        backgroundTask.execute();
    }

    public void parseJSON( ) {
        if (json_string == null) {
            //  Toast.makeText(, "FIRST GET JSON", Toast.LENGTH_LONG).show();
        } else {
         /*Intent intent = new Intent(this, DisplayListView.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);
      */
            //  Toast.makeText(getApplicationContext(), "JSON got", Toast.LENGTH_LONG).show();
        }

    }

    public void getJSON() {
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://bustracker.ga/json_get_data1.php";


        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                // JSON_STRING=null;
                // json_string=null;
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
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
            //  TextView textView = (TextView) findViewById(R.id.jsonview);
            //  textView.setText(result);
            json_string = result;
        }
    }
}