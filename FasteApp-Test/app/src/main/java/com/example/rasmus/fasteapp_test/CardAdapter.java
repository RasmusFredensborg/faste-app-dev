package com.example.rasmus.fasteapp_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailView;

import org.json.JSONArray;
import org.json.JSONException;

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private JSONArray videoMetadata;
    private ThumbnailListener thumbnailListener;
    private String videos [] = {""};

    public CardAdapter(Context c, JSONArray videoMetadata){
        mContext = c;
        thumbnailListener = new ThumbnailListener();
        this.videoMetadata = videoMetadata;
    }

    @Override
    public int getCount() {
        return videoMetadata.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String videoID = "";
        String title = "";
        String viewCount = "";
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView =  inflater.inflate(R.layout.grid_card_item,parent,false);
            YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) gridView.findViewById(R.id.thumbnail);
            TextView descriptionTxt = (TextView) gridView.findViewById(R.id.cardDescription);
            TextView viewCountTxt = (TextView) gridView.findViewById(R.id.viewCount);
            try {
                videoID = videoMetadata.getJSONObject(position).getString("id");
                title = videoMetadata.getJSONObject(position).getJSONObject("snippet").getString("title");
                viewCount = videoMetadata.getJSONObject(position).getJSONObject("statistics").getString("viewCount");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            thumbnail.initialize(DeveloperKey.BROWSER_KEY,thumbnailListener);
            thumbnail.setTag(videoID);
            descriptionTxt.setText(title);
            viewCountTxt.setText(viewCount);

        } else {
            gridView = convertView;
        }

        return gridView;

    }
}
