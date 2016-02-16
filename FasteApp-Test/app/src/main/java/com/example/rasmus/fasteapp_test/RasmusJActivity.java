package com.example.rasmus.fasteapp_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

/**
 * Created by rasmus on 16-02-2016.
 */
public class RasmusJActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rasmus_j);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new CardAdapter(this));
    }



}

