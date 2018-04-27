package com.vcoders.on_demand_youtube_player.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;
import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.interfaces.IInitYoutubePlayerListener;
import com.vcoders.on_demand_youtube_player.services.YoutubeAPI;

public class YoutubePlayerCustom extends LinearLayout implements YouTubePlayer.OnInitializedListener {

    private Context context;
    private IInitYoutubePlayerListener youtubePlayerListener;
    private YouTubePlayer mYoutubePlayer;

    private ImageView imgPlay;

    public YoutubePlayerCustom(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public YoutubePlayerCustom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public YoutubePlayerCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        ViewGroup parent = (ViewGroup) this.getParent();
        if (parent != null)
            parent.removeView(this);

        View view = View.inflate(context, R.layout.layout_youtube_player_custom, this);

    }

    public void initYoutubePlayer(Fragment fragment, IInitYoutubePlayerListener youtubePlayerListener) {
        this.youtubePlayerListener = youtubePlayerListener;

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = fragment.getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtubeLayout, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(YoutubeAPI.getInstance().API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            mYoutubePlayer = youTubePlayer;
            youtubePlayerListener.onInitYoutubePlayerSuccess(youTubePlayer);
        }
    }

    public void playVideo(YouTubePlayer youTubePlayer, String videoId) {
        if (youTubePlayer != null) {
            youTubePlayer.loadVideo(videoId);
            youTubePlayer.play();
        }
    }

    public void pauseVideo(YouTubePlayer youTubePlayer) {
        if (youTubePlayer != null)
            youTubePlayer.pause();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        youtubePlayerListener.onInitYoutubePlayerFailure(youTubeInitializationResult.toString());
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.imgPlay:
//                if (mYoutubePlayer != null) {
//                    if (mYoutubePlayer.isPlaying()) {
//                        mYoutubePlayer.pause();
//                        Picasso.with(context).load(R.mipmap.img_pause).into(imgPlay);
//                    } else {
//                        mYoutubePlayer.play();
//                        Picasso.with(context).load(R.mipmap.img_play).into(imgPlay);
//                    }
//                }
//                break;
//        }
//    }
}
