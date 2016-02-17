package com.example.rasmus.fasteapp_test; /**
 * Created by rasmus on 16-02-2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rasmus.fasteapp_test.DeveloperKey;
import com.example.rasmus.fasteapp_test.R;
import com.example.rasmus.fasteapp_test.ThumbnailListener;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private String [] videos;
    private ThumbnailListener thumbnailListener;

    public CardAdapter(Context c, String [] videos){
        mContext = c;
        thumbnailListener = new ThumbnailListener();
        this.videos = videos;
    }

    @Override
    public int getCount() {
        return videos.length;
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
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView =  inflater.inflate(R.layout.grid_card_item,parent,false);
            YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) gridView.findViewById(R.id.thumbnail);

            thumbnail.initialize(DeveloperKey.DEVELOPER_KEY,thumbnailListener);
            thumbnail.setTag(videos[position]);

        } else {
            gridView = convertView;
        }

        return gridView;

    }
}
