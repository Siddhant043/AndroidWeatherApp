package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView weatherDetails;
    EditText getCity;

    public void getWeather(View view){
        String url = "https://jsonplaceholder.typicode.com/todos/1";
        String result = "";
        DownloadTask task = new DownloadTask();
        try {
            result = task.execute(url).get();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Accessing JSON Object same as we access web content
    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine()) != null){
                    result.append(line).append("\n");
                }
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // To get a particular part of JSON Object
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                String titleInfo = jsonObject.getString("title");
                Log.i("JSON", titleInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherDetails = findViewById(R.id.detailsTextView);
        getCity = findViewById(R.id.cityText);
    }
}