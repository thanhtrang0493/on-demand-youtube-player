package com.vcoders.on_demand_youtube_player.audio;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.audio.handlers.DelayedStopHandler;
import com.vcoders.on_demand_youtube_player.audio.handlers.ServiceHandler;
import com.vcoders.on_demand_youtube_player.audio.interfaces.Playback;
import com.vcoders.on_demand_youtube_player.audio.interfaces.YouTubeVideoUpdateListener;
import com.vcoders.on_demand_youtube_player.audio.notification.MediaNotificationManager;
import com.vcoders.on_demand_youtube_player.audio.playback.LocalPlayback;
import com.vcoders.on_demand_youtube_player.audio.playback.PlaybackManager;
import com.vcoders.on_demand_youtube_player.audio.playback.QueueManager;
import com.vcoders.on_demand_youtube_player.audio.receiver.MediaButtonIntentReceiver;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.utils.LogHelper;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import java.util.List;

import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DURATION;
import static android.telephony.PhoneStateListener.LISTEN_CALL_STATE;
import static android.telephony.TelephonyManager.CALL_STATE_IDLE;
import static android.telephony.TelephonyManager.CALL_STATE_OFFHOOK;
import static android.telephony.TelephonyManager.CALL_STATE_RINGING;
import static com.vcoders.on_demand_youtube_player.audio.BackgroundAudioService.ACTION_PREVIOUS;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.ACTION_NEXT;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.ACTION_PAUSE;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.ACTION_PLAY;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.ACTION_STOP;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.INTENT_SESSION_TOKEN;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.KEY_SESSION_TOKEN;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.KEY_YOUTUBE_TYPE;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.KEY_YOUTUBE_TYPE_PLAYLIST;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.KEY_YOUTUBE_TYPE_PLAYLIST_VIDEO_POS;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.KEY_YOUTUBE_TYPE_VIDEO;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_MEDIA_NO_NEW_REQUEST;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_MEDIA_TYPE_PLAYLIST;
import static com.vcoders.on_demand_youtube_player.audio.config.Config.YOUTUBE_MEDIA_TYPE_VIDEO;

/**
 * Service class for background youtube playback
 */
public class BackgroundExoAudioService extends Service implements PlaybackManager.PlaybackServiceCallback {
    private static final String TAG = LogHelper.makeLogTag(BackgroundExoAudioService.class);

    // Delay stopSelf by using a handler.
    private static final int STOP_DELAY = 30000;

    private PlaybackManager playbackManager;

    private MediaSessionCompat mediaSession;
    private MediaControllerCompat mediaController;
    private MediaNotificationManager mediaNotificationManager;

    private LocalBroadcastManager mLocalBroadcastManager;

    private final DelayedStopHandler delayedStopHandler = new DelayedStopHandler(this);
    private ServiceHandler serviceHandler = new ServiceHandler(this);

    private int mediaType = YOUTUBE_MEDIA_NO_NEW_REQUEST;

    private Context context;

    private Video currentYouTubeVideo;
    private String currentVideoTitle;
    private int currentVideoPosition;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("WifiManagerPotentialLeak")
    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        currentYouTubeVideo = new Video();
        currentVideoPosition = -1;
//        mediaButtonFilter.setPriority(1000); //this line sets receiver priority

        initMediaSessions();
        initPhoneCallListener();
    }

    @Override
    public void onDestroy() {
//        if (mediaButtonIntentReceiver != null)
//            unregisterReceiver(mediaButtonIntentReceiver);

        mediaNotificationManager.stopNotification();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        LogHelper.e(TAG, "onStartCommand | intent: " + intent.getAction());
//        handleIntent(intent);
//        return super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            LogHelper.e(TAG, "onStartCommand | intent: " + intent);
            handleIntent(intent);
        }
        // Reset the delay handler to enqueue a message to stop the service if
        // nothing is playing.
        delayedStopHandler.removeCallbacksAndMessages(null);
        delayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY);

        serviceHandler.removeCallbacksAndMessages(null);
        serviceHandler.sendEmptyMessage(0);
        return START_STICKY;
    }

    /**
     * Callback method called from PlaybackManager whenever the music is about to play.
     */
    @Override
    public void onPlaybackStart() {
        mediaSession.setActive(true);
        delayedStopHandler.removeCallbacksAndMessages(null);

        // The service needs to continue running even after the bound client (usually a
        // MediaController) disconnects, otherwise the playback will stop.
        // Calling startService(Intent) will keep the service running until it is explicitly killed.
        startService(new Intent(getApplicationContext(), BackgroundExoAudioService.class));
    }

    @Override
    public void onNotificationRequired() {
        mediaNotificationManager.startNotification();

        MediaMetadataCompat videoMetadata = getVideoMetadata();
        if (videoMetadata != null) {
            mediaSession.setMetadata(videoMetadata);
        }
    }

    @Override
    public void onPlaybackStop() {
        mediaSession.setActive(false);
        // Reset the delayed stop handler, so after STOP_DELAY it will be executed again,
        // potentially stopping the service.
        delayedStopHandler.removeCallbacksAndMessages(null);
        delayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY);
        stopForeground(true);
    }

    @Override
    public void onPlaybackStateUpdated(PlaybackStateCompat newState) {
        mediaSession.setPlaybackState(newState);
    }

    /**
     * Initializes media sessions and receives media events
     */
    private void initMediaSessions() {
        // Make sure the media player will acquire a wake-lock while playing. If we don't do
        // that, the CPU might go to sleep while the song is playing, causing playback to stop.
        //
        // Remember that to use this, we have to declare the android.permission.WAKE_LOCK
        // permission in AndroidManifest.xml.
//        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);

        Context context = getApplicationContext();
//        Intent intent = new Intent(context, NowPlayingActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(
//                context,
//                99 /*request code*/,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );

        ComponentName eventReceiver = new ComponentName(
                context.getPackageName(),
                MediaButtonIntentReceiver.class.getName()
        );

        PendingIntent buttonReceiverIntent = PendingIntent.getBroadcast(
                context,
                0,
                new Intent(Intent.ACTION_MEDIA_BUTTON),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        QueueManager queueManager = new QueueManager(
                getResources(),
                new YouTubeVideoUpdateListener() {
                    @Override
                    public void onYouTubeVideoChanged(Video youTubeVideo) {
                        if (youTubeVideo == null) return;
                        LogHelper.e(TAG, "onYouTubeVideoChanged: " + youTubeVideo);

                        currentYouTubeVideo = youTubeVideo;

                        MediaMetadataCompat videoMetadata = getVideoMetadata();
                        if (videoMetadata != null) {
                            mediaSession.setMetadata(videoMetadata);
                        }
                    }

                    @Override
                    public void onYouTubeVideoRetrieveError() {
                        playbackManager.updatePlaybackState(getString(R.string.error_no_yt_video_retrieve));
                    }

                    @Override
                    public void onCurrentQueueIndexUpdated(int queueIndex) {
                        playbackManager.handlePlayRequest();
                    }

                    @Override
                    public void onQueueUpdated(String title, List<Video> newQueue) {
//                        mediaSession.setQueue(newQueue);
                        mediaSession.setQueueTitle(title);
                    }
                }
        );

        LocalPlayback playback = new LocalPlayback(this);
        playbackManager = new PlaybackManager(this, getResources(), queueManager, playback);

        mediaSession = new MediaSessionCompat(
                context,
                TAG,
                eventReceiver,
                buttonReceiverIntent
        );


        playbackManager.updatePlaybackState(null);
        try {
            mediaController = new MediaControllerCompat(
                    context,
                    mediaSession.getSessionToken()
            );

            mediaSession.setCallback(playbackManager.getMediaSessionCallback());
            mediaNotificationManager = new MediaNotificationManager(this);
//            sendSessionTokenToActivity();
//            mediaSession.setSessionActivity(pi);
        } catch (RemoteException re) {
            re.printStackTrace();
            throw new IllegalStateException("Could not create a MediaNotificationManager", re);
        }
    }

    private void initPhoneCallListener() {
        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == CALL_STATE_RINGING) {
                    // Incoming call: Pause music
                    playbackManager.handlePauseRequest();
                } else if (state == CALL_STATE_IDLE) {
                    // Not in call: Play music
                    playbackManager.handlePlayRequest();
                } else if (state == CALL_STATE_OFFHOOK) {
                    // A call is dialing, active or on hold
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };

        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr == null) return;
        mgr.listen(phoneStateListener, LISTEN_CALL_STATE);

    }

    public MediaSessionCompat.Token getSessionToken() {
        return mediaController.getSessionToken();
    }

    public void sendSessionTokenToActivity() {
//        LogHelper.e(TAG, "ServiceHandler | handleMessage sending: INTENT_SESSION_TOKEN");
        Intent intent = new Intent(INTENT_SESSION_TOKEN);
        Bundle b = new Bundle();
        b.putParcelable(KEY_SESSION_TOKEN, getSessionToken());
        intent.putExtra(KEY_SESSION_TOKEN, b);
        sendBroadcast(intent);
    }

    /**
     * Handles intent (player options play/pause/stop...)
     *
     * @param intent provides the player actions
     */
    private void handleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null)
            return;
        String action = intent.getAction();
        if (action.equalsIgnoreCase(ACTION_PLAY)) {
            handleMedia(intent);
            mediaController.getTransportControls().play();
        } else if (action.equalsIgnoreCase(ACTION_PAUSE)) {
            mediaController.getTransportControls().pause();
        } else if (action.equalsIgnoreCase(ACTION_PREVIOUS)) {
            mediaController.getTransportControls().skipToPrevious();
        } else if (action.equalsIgnoreCase(ACTION_NEXT)) {
            mediaController.getTransportControls().skipToNext();
        } else if (action.equalsIgnoreCase(ACTION_STOP)) {
            mediaController.getTransportControls().stop();
        }
    }

    /**
     * Handles media - playlist and videos sent from fragments
     *
     * @param intent provides the Media Type
     */
    private void handleMedia(Intent intent) {
        int intentMediaType = intent.getIntExtra(KEY_YOUTUBE_TYPE, YOUTUBE_MEDIA_NO_NEW_REQUEST);

        switch (intentMediaType) {
            // Video has been paused. It is not necessary a new playback requests
            case YOUTUBE_MEDIA_NO_NEW_REQUEST:
                LogHelper.e(TAG, "handleMedia: YOUTUBE_MEDIA_NO_NEW_REQUEST");
                playbackManager.handlePlayRequest();
                break;
            case YOUTUBE_MEDIA_TYPE_VIDEO:
                mediaType = YOUTUBE_MEDIA_TYPE_VIDEO;
                currentYouTubeVideo = intent.getParcelableExtra(KEY_YOUTUBE_TYPE_VIDEO);
                if (currentYouTubeVideo.getId() != null) {
                    playbackManager.initPlaylist(currentYouTubeVideo, null);
                    LogHelper.e(TAG, "handleMedia: YOUTUBE_MEDIA_TYPE_VIDEO");
                    playbackManager.handlePlayRequest();
                    playbackManager.updateYouTubeVideo();
                }
                break;
            // New playlist playback request
            case YOUTUBE_MEDIA_TYPE_PLAYLIST:
                mediaType = YOUTUBE_MEDIA_TYPE_PLAYLIST;

                List<Video> youTubeVideos = (List<Video>) intent.getParcelableExtra(KEY_YOUTUBE_TYPE_PLAYLIST);
                currentVideoPosition = intent.getIntExtra(KEY_YOUTUBE_TYPE_PLAYLIST_VIDEO_POS, 0);
                LogHelper.e(TAG, "currentVideoPosition: " + currentVideoPosition);
                if (youTubeVideos != null && currentVideoPosition != -1) {
                    currentYouTubeVideo = youTubeVideos.get(currentVideoPosition);
                    playbackManager.initPlaylist(currentYouTubeVideo, youTubeVideos);
                    LogHelper.e(TAG, "handleMedia: YOUTUBE_MEDIA_TYPE_PLAYLIST");
                    playbackManager.handlePlayRequest();
                    playbackManager.updateYouTubeVideo();
                }
                break;
            default:
                LogHelper.e(TAG, "Unknown command");
                break;
        }
    }


    /**
     * Generates specific action with parameters below
     *
     * @param icon         the icon number
     * @param title        the title of the notification
     * @param intentAction the action
     * @return NotificationCompat.Action
     */
    private NotificationCompat.Action generateAction(int icon, String title, String intentAction) {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, BackgroundExoAudioService.class);
        intent.setAction(intentAction);
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, intent, 0);

        return new NotificationCompat.Action.Builder(icon, title, pendingIntent).build();
    }

    /**
     * Generates specific action with parameters below
     *
     * @param intentAction the action
     * @return NotificationCompat.Action
     */
    private NotificationCompat.Action generateIntentAction(String intentAction) {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, BackgroundExoAudioService.class);
        intent.setAction(intentAction);
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, intent, 0);

        String label;
        int icon;
        switch (intentAction) {
            case ACTION_PAUSE:
                label = getString(R.string.action_pause);
                icon = R.mipmap.ic_pause_white_24dp;
                break;
            case ACTION_PLAY:
                label = getString(R.string.action_play);
                icon = R.mipmap.ic_play_arrow_white_24dp;
                break;
            case ACTION_NEXT:
                label = getString(R.string.action_next);
                icon = R.mipmap.ic_skip_next_white_24dp;
                break;
            case ACTION_PREVIOUS:
                label = getString(R.string.action_previous);
                icon = R.mipmap.ic_skip_previous_white_24dp;
                break;
            default:
                return null;
        }

        return new NotificationCompat.Action.Builder(icon, label, pendingIntent).build();
    }

//    @Override
//    public void onTaskRemoved(Intent rootIntent)
//    {
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context
//                .NOTIFICATION_SERVICE);
//        notificationManager.cancel(NOTIFICATION_ID);
//    }

    private MediaMetadataCompat getVideoMetadata() {
        if (currentYouTubeVideo == null) return null;

        MediaMetadataCompat.Builder ytVideo = new MediaMetadataCompat.Builder();
        ytVideo.putString(METADATA_KEY_DISPLAY_TITLE, currentYouTubeVideo.getSnippet() != null ? currentYouTubeVideo.getSnippet().getTitle() : "");
        ytVideo.putString(METADATA_KEY_DISPLAY_SUBTITLE, Utils.getInstance().formatViewCount(currentYouTubeVideo.getContentDetails() == null ? null :
                Utils.getInstance().convertIntToString(currentYouTubeVideo.getContentDetails().getItemCount())));
        String thumbnail = "";
        if (currentYouTubeVideo.getSnippet() != null && currentYouTubeVideo.getSnippet().getThumbnails() != null &&
                currentYouTubeVideo.getSnippet().getThumbnails().getMedium() != null && currentYouTubeVideo.getSnippet().getThumbnails().getMedium().getUrl() != null) {
            thumbnail = currentYouTubeVideo.getSnippet().getThumbnails().getMedium().getUrl();
        }
        ytVideo.putString(METADATA_KEY_DISPLAY_ICON_URI, thumbnail);
        ytVideo.putLong(METADATA_KEY_DURATION, playbackManager.getDuration());

        return ytVideo.build();
    }

    public Video getCurrentYouTubeVideo() {
        return currentYouTubeVideo;
    }

    public Playback getPlayback() {
        if (playbackManager == null) return null;
        else return playbackManager.getPlayback();
    }
}