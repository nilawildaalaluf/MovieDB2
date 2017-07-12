package com.nila.wilda.moviedb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by wilda on 7/8/17.
 */

public class YoutubeActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener{

    DetailActivity D = new DetailActivity();

    private static final String API_KEY = "AIzaSyBCqT2PhCbQ8oS8hqb7Rf-GrASGVelwWGs";
    private static final int RECOVERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        YouTubePlayerSupportFragment frag =
                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        frag.initialize(API_KEY, this);

//        getSupportActionBar().setTitle("Trailer");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

//    @Override
//    public boolean onSupportNavigateUp(){
//        finish();
//        return true;
//    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(D.key); // plays https://www.youtube.com/watch?v=462PvcFUHR8
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }
    }
}
