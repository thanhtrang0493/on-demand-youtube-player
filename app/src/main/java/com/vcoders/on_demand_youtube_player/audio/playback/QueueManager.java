package com.vcoders.on_demand_youtube_player.audio.playback;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.audio.config.QueueHelper;
import com.vcoders.on_demand_youtube_player.audio.interfaces.YouTubeVideoUpdateListener;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.utils.LogHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Jun-10
 */

public class QueueManager
{
    private static final String TAG = LogHelper.makeLogTag(QueueManager.class);

    private YouTubeVideoUpdateListener youTubeVideoUpdateListener;
    private Resources resources;

    // "Now playing" queue:
    private List<Video> playingQueue;
    private int currentIndex;

    public QueueManager(@NonNull Resources resources,
                        @NonNull YouTubeVideoUpdateListener listener)
    {
        this.youTubeVideoUpdateListener = listener;
        this.resources = resources;

        playingQueue = Collections.synchronizedList(new ArrayList<Video>());
        currentIndex = 0;
    }

    private void setCurrentQueueIndex(int index)
    {
        if (index >= 0 && index < playingQueue.size()) {
            currentIndex = index;
            youTubeVideoUpdateListener.onCurrentQueueIndexUpdated(currentIndex);
        }
    }

    public boolean setCurrentQueueItem(String youTubeVideoId)
    {
        // Set the current index on queue from the queue Id:
        int index = QueueHelper.getVideoIndexOnQueue(playingQueue, youTubeVideoId);
        setCurrentQueueIndex(index);
        return index >= 0;
    }

    public boolean skipQueuePosition(int amount)
    {
        int index = currentIndex + amount;
        if (index < 0) {
            // skip backwards before the first song will keep you on the first song
            index = 0;
        } else {
            // skip forwards when in last song will cycle back to start of the queue
            index %= playingQueue.size();
        }
        if (!QueueHelper.isIndexPlayable(index, playingQueue)) {
            LogHelper.e(TAG, "Cannot increment queue index by ", amount,
                    ". Current=", currentIndex, " queue length=", playingQueue.size());
            return false;
        }
        currentIndex = index;
        return true;
    }

    public Video getCurrentVideo()
    {
        if (!QueueHelper.isIndexPlayable(currentIndex, playingQueue)) {
            return null;
        }
        return playingQueue.get(currentIndex);
    }

    public int getCurrentVideoIndex(String youTubeVideoId)
    {
        // Set the current index on queue from the queue Id:
        return QueueHelper.getVideoIndexOnQueue(playingQueue, youTubeVideoId);
    }

    public int getCurrentQueueSize()
    {
        if (playingQueue == null) {
            return 0;
        }
        return playingQueue.size();
    }

    public void setCurrentQueue(String title, List<Video> newQueue)
    {
        setCurrentQueue(title, newQueue, null);
    }

    public void setCurrentQueue(Video initialVideo, List<Video> newQueue)
    {
        playingQueue = newQueue;
        if (newQueue == null) {
            playingQueue = Collections.synchronizedList(new ArrayList<Video>());
            playingQueue.add(initialVideo);
        }

        int index = 0;
        if (initialVideo != null) {
            index = QueueHelper.getYouTubeVideoIndexOnQueue(playingQueue, initialVideo.getId());
        }
        currentIndex = Math.max(index, 0);
        youTubeVideoUpdateListener.onQueueUpdated(
                resources.getString(R.string.fragment_tab_favorites),
                newQueue
        );
    }

    public void setCurrentQueue(String title, List<Video> newQueue,
                                String initialVideoId)
    {
        playingQueue = newQueue;
        int index = 0;
        if (initialVideoId != null) {
            index = QueueHelper.getYouTubeVideoIndexOnQueue(playingQueue, initialVideoId);
        }
        currentIndex = Math.max(index, 0);
        youTubeVideoUpdateListener.onQueueUpdated(title, newQueue);
    }

    public void updateYouTubeVideo()
    {
        Video currentYouTubeVideo = getCurrentVideo();
        if (currentYouTubeVideo == null) {
            youTubeVideoUpdateListener.onYouTubeVideoRetrieveError();
            return;
        }

        youTubeVideoUpdateListener.onYouTubeVideoChanged(currentYouTubeVideo);
    }
}
