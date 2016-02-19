package com.example.rasmus.fasteapp_test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rasmus on 16-02-2016.
 */
public class RasmusJActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String [] videos = {"jaqymceidB8","lknrWK_rhb4","gtTfd6tISfw"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rasmus_j);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new CardAdapter(this, videos));
        gridView.setOnItemClickListener(this);
        new getDataSetGrid().execute(videos);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(view.getContext(), YTtest2.class);
        intent.putExtra("video", videos[position]);
        view.getContext().startActivity(intent);
    }
    // ADD TRY AND FINALLY TO CLOSE READERS
    private class getDataSetGrid extends AsyncTask<String, String, JSONArray> {
        protected JSONArray doInBackground(String... urls) {
            int count = urls.length;
            JSONArray jsonToReturn = new JSONArray();
            try {
                for (int i = 0; i < count; i++) {
                    JSONObject temp;
                    URL url = new URL("https://www.googleapis.com/youtube/v3/videos?id=-Wnh9FYrcoE&key=AIzaSyBtaux8WdLQ1bbcS30tstKU7khSOls86Yg&part=statistics");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder stringBuilder = new StringBuilder();

                    String inputString;
                    while((inputString = streamReader.readLine()) != null) {
                        stringBuilder.append(inputString);
                    }
                    temp = new JSONObject(stringBuilder.toString());
                    jsonToReturn.put(temp);
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return jsonToReturn;
        }

        protected void onPostExecute(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                String id = "";
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONArray jA = jsonObject.getJSONArray("items");
                    JSONObject jo = jA.getJSONObject(0);
                    id = jo.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(Integer.toString(i), id);
            }
        }
    }
}

