package com.example.rasmus.fasteapp_test;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    private GridView gridView;

    private JSONArray videoMetadata;
    private String videos = "jaqymceidB8,lknrWK_rhb4,gtTfd6tISfw";
    private String googleAPI = "https://www.googleapis.com/youtube/v3/videos?id=";
    private String keyPrefix = "&key=";
    private String fieldsAndPart = "&fields=items(id,snippet(title,description),statistics(viewCount))&part=snippet,statistics";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rasmus_j);
        gridView = (GridView) findViewById(R.id.grid_view);
        videoMetadata = new JSONArray();
        new getVideoMetaData().execute(videos);
    }

    /*
    Gets the position of the card pressed and sends an intent with the matching position in the videoMetadata array
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JSONObject videoToSend = new JSONObject();
        try {
            videoToSend = videoMetadata.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(view.getContext(), YTtest2.class);
        intent.putExtra("videoData", videoToSend.toString());
        view.getContext().startActivity(intent);
    }
    // ADD TRY AND FINALLY TO CLOSE READERS
    /*
    Class that asynchronously fetches metadata for all the videos in the videos String.
    The metadata is saved in a JSONObject and returned to the onPostExecute method.
    In the onPostExecute method the items array is fetched from the JSONObject and stored.
    Lastly the gridView is populated with the metadata.
     */
    private class getVideoMetaData extends AsyncTask<String, String, JSONObject> {

        protected JSONObject doInBackground(String... videoURLS) {
            JSONObject jsonObjectToReturn = new JSONObject();
            try {
                URL url = new URL(googleAPI+videoURLS[0]+keyPrefix+DeveloperKey.BROWSER_KEY+fieldsAndPart); //-Wnh9FYrcoE&key=AIzaSyBtaux8WdLQ1bbcS30tstKU7khSOls86Yg&part=statistics");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();

                String inputString;
                while((inputString = streamReader.readLine()) != null) {
                    stringBuilder.append(inputString);
                }
                jsonObjectToReturn = new JSONObject(stringBuilder.toString());
                urlConnection.disconnect();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return jsonObjectToReturn;
        }

        protected void onPostExecute(JSONObject jsonObject) {
            try {
                videoMetadata = jsonObject.getJSONArray("items");

                String videoID = videoMetadata.getJSONObject(0).getString("id");
                String title = videoMetadata.getJSONObject(0).getJSONObject("snippet").getString("title");
                String viewCount = videoMetadata.getJSONObject(0).getJSONObject("statistics").getString("viewCount");

                Log.d("id", videoID);
                Log.d("title", title);
                Log.d("viewCount", viewCount);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            for (int i = 0; i < jsonArray.length(); i++) {
//                try {
//                    JSONObject videoData = jsonArray.getJSONObject(i).getJSONArray("items").getJSONObject(0);
//                    videoMetadata.put(videoData);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }

            Log.d("hmm", videoMetadata.toString());
            gridView.setAdapter(new CardAdapter(RasmusJActivity.this, videoMetadata));
            gridView.setOnItemClickListener(RasmusJActivity.this);
        }
    }
}

