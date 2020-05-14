package com.example.baher.fyeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;



public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private String GOOGLE_API_KEY = "AIzaSyAG5Z09j5AVgP4Zc-ih9x8iTN1bwWAh774";

    private static final String TAG = YoutubeActivity.class.getSimpleName();
    private String YOUTUBE_VIDEO_ID;
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    // YouTube player view
    private YouTubePlayerView youTubeView;

    private View decorView;
    private final int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_youtube);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_player);

        // Initializing video player with developer key
        youTubeView.initialize(GOOGLE_API_KEY, this);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        Log.i("","No problem here!");
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
            Log.i("","No problem here!");
        } else {
            String errorMessage = String.format(
                    "There is an error: ", errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            Log.i("","No problem here!");
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        Log.i("","No problem here!");
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically

            Intent i = getIntent();
            Bundle b = i.getExtras();
            String code = b.getString("Code");
            player.loadVideo(code);

            Log.i("code", code);
            Log.i("","No problem here!");
            // Hiding player controls
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(GOOGLE_API_KEY, this);
            Log.i("","No problem here!");
        }
        else
            Log.i("","No problem here!");
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        Log.i("","No problem here!");
        return (YouTubePlayerView) findViewById(R.id.youtube_player);
    }
}

