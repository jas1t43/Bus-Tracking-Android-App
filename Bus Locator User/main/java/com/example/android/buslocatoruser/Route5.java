package com.example.android.buslocatoruser;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Route5 extends AppCompatActivity {
    String JSON_STRING;
    String json_string;

    String source, destination;
    String source1, destination1;
    Spinner spsr, spds;
    ArrayAdapter<CharSequence> adapters, adapterd;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route5);
        spds = (Spinner) findViewById(R.id.spinnerdst5);
        spsr = (Spinner) findViewById(R.id.spinner_src5);
        adapters = ArrayAdapter.createFromResource(this, R.array.strsrc, android.R.layout.simple_spinner_item);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterd = ArrayAdapter.createFromResource(this, R.array.strdes, android.R.layout.simple_spinner_item);
        adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spsr.setAdapter(adapters);
        spds.setAdapter(adapterd);
        spsr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                source1 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                source1 = "";
            }
        });
        spds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destination1 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                destination1 = "";
            }
        });
    }


    public void findFare(View view) {
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(source1, destination1);
        //finish();
    }

    class BackgroundTask extends AsyncTask<String, Void, String> {
        String json_url;

        @Override
        public void onPreExecute() {
            json_url = "http://bustracker.ga/json_get_data6.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String source = params[0];
            String destination = params[1];
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("source", "UTF-8") + "=" + URLEncoder.encode(source, "UTF-8") + "&" +
                        URLEncoder.encode("destination", "UTF-8") + "=" + URLEncoder.encode(destination, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");

                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException i) {
                i.printStackTrace();

            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            if(!result.equals("Wrong Route....Bus available on route1"))
            {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                TextView tv=(TextView)findViewById(R.id.fare5);
                tv.setText(result);

            }
            else
            {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                TextView tv=(TextView)findViewById(R.id.fare5);
                tv.setText("0");
            }
            //  json_string=result;
        }
    }
}



