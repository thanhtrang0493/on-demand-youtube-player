package com.vcoders.on_demand_youtube_player.features.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.widget.LinearLayout;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.ApplicationModule;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.audio.PlaybackControlsFragment;
import com.vcoders.on_demand_youtube_player.audio.config.NetworkHelper;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.playlistByTopic.PlaylistByTopicFragment;
import com.vcoders.on_demand_youtube_player.features.searchVideos.SearchVideosFragment;
import com.vcoders.on_demand_youtube_player.model.Topic;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.utils.LogHelper;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import static android.support.v4.media.session.PlaybackStateCompat.STATE_ERROR;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_NONE;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_STOPPED;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.INTENT_SESSION_TOKEN;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.KEY_SESSION_TOKEN;

public class HomeActivity extends BaseActivity implements HomeView {

    HomeComponent homeComponent;
    public static List<Topic> topics;

    @Inject
    HomePresenter homePresenter;

    @Inject
    HomeRouter homeRouter;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        getBundle();
        networkConf = new NetworkHelper(this);
        fragmentManager = getSupportFragmentManager();
        playbackControlsFragment = (PlaybackControlsFragment) fragmentManager
                .findFragmentById(R.id.fragment_playback_controls);
    }

    private void getBundle() {
        topics = (List<Topic>) getIntent().getExtras().getSerializable(Constant.TOPICS);
        if (topics.size() > 0) {
            Topic topic = topics.get(0);
            topic.setSelect(true);
            topics.set(0, topic);
        }
    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_home;
    }

    @Override
    protected String getTitleActionBar() {
        return null;
    }

    @Override
    protected TypeActionBar[] getTypeActionBar() {
        return new TypeActionBar[0];
    }

    @Override
    protected BaseRouter getRouter() {
        return homeRouter;
    }

    @Override
    protected BasePresenter getPresenter() {
        return homePresenter;
    }

    @Override
    protected void inject() {
        homeComponent = DaggerHomeComponent.builder().
                applicationModule(new ApplicationModule(this)).build();
        homeComponent.inject(this);
    }

    @Override
    protected BaseComponent getActivityComponent() {
        return homeComponent;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void onSearchClick() {
        super.onSearchClick();
        changeFragment(new SearchVideosFragment(), null);
    }

    @Override
    public void onCloseClick() {
        super.onCloseClick();
    }


//    audio
    private FragmentTransaction lastTransaction;
    private MediaSessionCompat.Token sessionToken;
    private NetworkHelper networkConf;
    private static final String TAG = HomeActivity.class.getSimpleName();
    private PlaybackControlsFragment playbackControlsFragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onStart()
    {
        super.onStart();
        LogHelper.e(TAG, "Main Activity onStart");
        if (playbackControlsFragment == null) {
            throw new IllegalStateException("Missing fragment with id 'controls'. Cannot continue.");
        }

        hidePlaybackControls();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(INTENT_SESSION_TOKEN);
        registerReceiver(messageReceiver, filter);

        if (sessionToken != null) {
            LogHelper.e(TAG, "on sessionToken receive");
            try {
                connectToSession(sessionToken);
                if (lastTransaction != null) {
                    lastTransaction.commit();
                    lastTransaction = null;
                }
            } catch (RemoteException re) {
                LogHelper.e(TAG, re, "could not connect media controller");
                hidePlaybackControls();
            }
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        LogHelper.d(TAG, "Main Activity onStop");
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(this);
        if (controller != null) {
            controller.unregisterCallback(mediaControllerCallback);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(messageReceiver);
    }

    // Callback that ensures that we are showing the controls
    private final MediaControllerCompat.Callback mediaControllerCallback = new MediaControllerCompat.Callback()
    {
        @Override
        public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state)
        {
            LogHelper.e(TAG, "onPlaybackStateChanged");
            if (shouldShowControls()) {
                showPlaybackControls();
            } else {
                LogHelper.e(TAG, "onPlaybackStateChanged: hiding controls cuz state = ", state.getState());
                hidePlaybackControls();
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata)
        {
            LogHelper.e(TAG, "onMetadataChanged");
            if (shouldShowControls()) {
                showPlaybackControls();
            } else {
                LogHelper.e(TAG, "onMetadataChanged: hiding controls because metadata is null");
                hidePlaybackControls();
            }
        }
    };

    private BroadcastReceiver messageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // Get extra data included in the Intent
            LogHelper.e(TAG, "on BroadcastReceiver receive");
            Bundle b = intent.getBundleExtra(KEY_SESSION_TOKEN);
            sessionToken = b.getParcelable(KEY_SESSION_TOKEN);
            if (sessionToken != null) {
                LogHelper.e(TAG, "on sessionToken receive");
                try {
                    connectToSession(sessionToken);
                } catch (RemoteException re) {
                    LogHelper.e(TAG, re, "could not connect media controller");
                    hidePlaybackControls();
                }
            }
        }
    };

    protected void showPlaybackControls()
    {
        LogHelper.e(TAG, "showPlaybackControls");
        if (networkConf.isNetworkAvailable(this)) {
            try {
                fragmentManager.beginTransaction()
                        .show(playbackControlsFragment)
//                        .commitAllowingStateLoss();
                        .commit();
            } catch (IllegalStateException ise) {
                // [According to](https://stackoverflow.com/questions/7575921)
//                ise.printStackTrace();
                lastTransaction = fragmentManager.beginTransaction()
                        .show(playbackControlsFragment);
            }
        }
    }

    protected void hidePlaybackControls()
    {
        LogHelper.e(TAG, "hidePlaybackControls");
        try {
            fragmentManager.beginTransaction()
                    .hide(playbackControlsFragment)
                    .commit();
        } catch (IllegalStateException ise) {
            // [According to](https://stackoverflow.com/questions/7575921)
//            ise.printStackTrace();
            lastTransaction = fragmentManager.beginTransaction()
                    .hide(playbackControlsFragment);
        }
    }

    /**
     * Check if the MediaSession is active and in a "playback-able" state
     * (not NONE and not STOPPED).
     *
     * @return true if the MediaSession's state requires playback controls to be visible.
     */
    protected boolean shouldShowControls()
    {
        LogHelper.e(TAG, "shouldShowControls");
        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(this);
        if (mediaController == null ||
                mediaController.getMetadata() == null ||
                mediaController.getPlaybackState() == null) {
            return false;
        }
        switch (mediaController.getPlaybackState().getState()) {
            case STATE_ERROR:
            case STATE_NONE:
            case STATE_STOPPED:
                return false;
            default:
                return true;
        }
    }

    private void connectToSession(MediaSessionCompat.Token token) throws RemoteException
    {
        LogHelper.e(TAG, "connectToSession");
        MediaControllerCompat mediaController = new MediaControllerCompat(this, token);
        MediaControllerCompat.setMediaController(this, mediaController);

        mediaController.registerCallback(mediaControllerCallback);

        if (shouldShowControls()) {
            showPlaybackControls();
        } else {
            LogHelper.e(TAG, "connectionCallback.onConnected: hiding controls because metadata is null");
            hidePlaybackControls();
        }

        if (playbackControlsFragment != null) {
            playbackControlsFragment.onConnected();
        }
    }
}
