package com.vcoders.on_demand_youtube_player.customView;


import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.services.YoutubeAPI;

public class YoutubePlayerViewCustom extends LinearLayout implements View.OnClickListener {

    private Context context;
    //    private YouTubePlayerFragment youTubePlayerView;
    private View llVideoControl;
    private ImageButton btnPlayVideo;
    private TextView txtPlayTime;
    private SeekBar sbVideoSeekbar;
    private YouTubePlayer mYoutubePlayer;
    private android.os.Handler mHandler;
    private String videoId;
    private boolean isPlay = false;

    public YoutubePlayerViewCustom(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public YoutubePlayerViewCustom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public YoutubePlayerViewCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        ViewGroup parent = (ViewGroup) this.getParent();
        if (parent != null)
            parent.removeView(this);

        View view = View.inflate(context, R.layout.layout_youtube_player_view, this);

        mHandler = new android.os.Handler();
        llVideoControl = view.findViewById(R.id.llVideoControl);
        btnPlayVideo = (ImageButton) view.findViewById(R.id.btnPlayVideo);
        btnPlayVideo.setOnClickListener(this);
        txtPlayTime = (TextView) view.findViewById(R.id.txtPlayTime);
        sbVideoSeekbar = (SeekBar) view.findViewById(R.id.sbVideoSeekbar);
        sbVideoSeekbar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    public void initYoutubePlayer(YouTubePlayer youTubePlayer, Boolean wasRestored) {
        if (youTubePlayer == null)
            return;

        this.mYoutubePlayer = youTubePlayer;

        displayCurrentTime();

        // Start buffering
        if (!wasRestored) {
            mYoutubePlayer.loadVideo(videoId);
            mYoutubePlayer.play();
        }

//        mYoutubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//        llVideoControl.setVisibility(View.VISIBLE);

        // Add listeners to YouTubePlayer instance
//        mYoutubePlayer.setPlayerStateChangeListener(mPlayerStateChangeListener);
//        mYoutubePlayer.setPlaybackEventListener(mPlaybackEventListener);

    }

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            long lengthPlayed = (mYoutubePlayer.getDurationMillis() * progress) / 100;
            mYoutubePlayer.seekToMillis((int) lengthPlayed);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public void initYoutubePlayerView(final String videoId, Fragment fragment) {
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = fragment.getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtubeLayout, youTubePlayerFragment).commit();

        if (youTubePlayerFragment != null)
            youTubePlayerFragment.initialize(YoutubeAPI.getInstance().API_KEY, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                   mYoutubePlayer=youTubePlayer;
                    if (!b && mYoutubePlayer != null) {
//                        mYoutubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

                        mYoutubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                        mYoutubePlayer.loadVideo(videoId);
                        mYoutubePlayer.play();
                    }
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
        this.videoId = videoId;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlayVideo:
                if (null != mYoutubePlayer) {
                    if (isPlay) {
                        btnPlayVideo.setImageResource(R.drawable.ic_play);
                        mYoutubePlayer.pause();
                        isPlay = false;
                    } else {
                        btnPlayVideo.setImageResource(R.drawable.ic_pause);
                        mYoutubePlayer.play();
                        isPlay = true;
                    }
                }
                break;
        }
    }

    YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
            mHandler.removeCallbacks(runnable);
        }

        @Override
        public void onPlaying() {
            mHandler.postDelayed(runnable, 100);
            displayCurrentTime();
        }

        @Override
        public void onSeekTo(int arg0) {
            mHandler.postDelayed(runnable, 100);
        }

        @Override
        public void onStopped() {
            mHandler.removeCallbacks(runnable);
        }
    };

    YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
            displayCurrentTime();
        }
    };

    private void displayCurrentTime() {
        if (null == mYoutubePlayer)
            return;
        String formattedTime = formatTime(mYoutubePlayer.getDurationMillis() - mYoutubePlayer.getCurrentTimeMillis());
        txtPlayTime.setText(formattedTime);
    }

    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        return (hours == 0 ? "--: " : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            displayCurrentTime();
            mHandler.postDelayed(this, 100);
        }
    };
}
