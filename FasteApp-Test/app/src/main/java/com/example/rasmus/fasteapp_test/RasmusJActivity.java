package com.example.rasmus.fasteapp_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by rasmus on 16-02-2016.
 */
public class RasmusJActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String [] videos = {"jaqymceidB8","lknrWK_rhb4"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rasmus_j);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new CardAdapter(this,videos));
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(view.getContext(), YTtest2.class);
        intent.putExtra("video", videos[position]);
        view.getContext().startActivity(intent);
    }

}

