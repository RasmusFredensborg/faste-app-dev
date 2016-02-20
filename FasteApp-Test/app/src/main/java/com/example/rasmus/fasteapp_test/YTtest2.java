package com.example.rasmus.fasteapp_test;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YTtest2 extends YouTubeFailureRecoveryActivity implements YouTubePlayer.OnFullscreenListener {

    final static int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    private YouTubePlayerView playerView;
    private YouTubePlayer player;
    private View otherViews;

    private boolean fullscreen;
    private String videoToPlay = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yttest2);

        Intent intent = getIntent();
        if (intent.getStringExtra("videoData") != null) {
            videoToPlay = intent.getStringExtra("video");
        }

        playerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        otherViews = findViewById(R.id.other_views);

        playerView.initialize(DeveloperKey.DEVELOPER_KEY, this);

        doLayout();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        setRequestedOrientation(PORTRAIT_ORIENTATION);
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
        player.setOnFullscreenListener(this);
        if (!wasRestored) {
            player.loadVideo(videoToPlay);
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return playerView;
    }

    private void doLayout() {
        LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) playerView.getLayoutParams();
        if(fullscreen) {
            playerParams.width = LayoutParams.MATCH_PARENT;
            playerParams.height = LayoutParams.MATCH_PARENT;

            otherViews.setVisibility(View.GONE);
        }
        else {
            otherViews.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams otherViewsParams = otherViews.getLayoutParams();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                playerParams.width = otherViewsParams.width = 0;
                playerParams.height = LayoutParams.WRAP_CONTENT;
                otherViewsParams.height = MATCH_PARENT;
                playerParams.weight = 1;
            } else {
                playerParams.width = otherViewsParams.width = MATCH_PARENT;
                playerParams.height = WRAP_CONTENT;
                playerParams.weight = 0;
                otherViewsParams.height = 0;
            }
        }
    }

    @Override
    public void onFullscreen(boolean isFullScreen) {
        fullscreen = isFullScreen;
        doLayout();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        doLayout();
    }
}
