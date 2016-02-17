package com.example.rasmus.fasteapp_test;

import android.content.Intent;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import static android.support.v4.app.ActivityCompat.startActivity;


/**
 * Created by rasmus on 16-02-2016.
 */
public final class ThumbnailListener implements
        YouTubeThumbnailView.OnInitializedListener,
        YouTubeThumbnailLoader.OnThumbnailLoadedListener {

    @Override
    public void onInitializationSuccess(
            YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
        loader.setOnThumbnailLoadedListener(this);

//        view.setImageResource(R.drawable.loading_thumbnail);
        String videoId = (String) view.getTag();
        loader.setVideo(videoId);
    }

    @Override
    public void onInitializationFailure(
            YouTubeThumbnailView view, YouTubeInitializationResult loader) {
//            view.setImageResource(R.drawable.no_thumbnail);
    }

    @Override
    public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
    }

    @Override
    public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
//            view.setImageResource(R.drawable.no_thumbnail);
    }
}
