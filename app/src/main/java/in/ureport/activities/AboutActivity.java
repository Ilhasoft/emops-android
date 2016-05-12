package in.ureport.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import in.ureport.R;
import in.ureport.helpers.YoutubePlayer;
import in.ureport.managers.MissionManager;

/**
 * Created by johncordeiro on 19/07/15.
 */
public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "AboutActivity";

    private static final String ABOUT_VIDEO_ID = "g4fGB5mQ_gE";

    private YouTubePlayerSupportFragment youtubeFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MissionManager.setThemeIfNeeded(this);
        setContentView(R.layout.activity_about);

        setupView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setupView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        YoutubePlayer youtubePlayer = new YoutubePlayer(this);

        youtubeFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.youtube_fragment);
        if(youtubeFragment != null) {
            youtubeFragment.initialize(youtubePlayer.getYoutubeKey(), onYoutubeInitializeListener);
        }
    }

    private void openPage(Uri parse) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, parse);
        startActivity(browserIntent);
    }

    private YouTubePlayer.OnInitializedListener onYoutubeInitializeListener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.cueVideo(ABOUT_VIDEO_ID);
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            Log.d(TAG, "onInitializationFailure() called with: " + "provider = [" + provider + "], youTubeInitializationResult = [" + youTubeInitializationResult + "]");
        }
    };

}