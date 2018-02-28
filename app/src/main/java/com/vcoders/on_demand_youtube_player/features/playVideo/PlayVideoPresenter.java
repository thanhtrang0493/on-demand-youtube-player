package com.vcoders.on_demand_youtube_player.features.playVideo;


import android.content.Context;
import android.net.Uri;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayVideoPresenter extends BasePresenter<PlayVideoView, PlayVideoRouter> {

    private Context context;

    public PlayVideoPresenter(Context context) {
        this.context = context;
    }

    public String getYouTubeVideoId(String videoUrl) {

        if (videoUrl != null && videoUrl.length() > 0) {

            Uri videoUri = Uri.parse(videoUrl);
            String video_id = videoUri.getQueryParameter("v");

            return video_id;
        }
        return "";
    }

    public String getYouTubePlaylistId(String playlistUrl) {

        if (playlistUrl != null && playlistUrl.length() > 0) {

            Uri playlistUri = Uri.parse(playlistUrl);
            String playlistId = playlistUri.getQueryParameter("list");

            return playlistId;
        }
        return "";
    }

    public String getYouTubeChannelId(String channelUrl) {

        if (channelUrl != null && channelUrl.length() > 0) {

            Uri channelUri = Uri.parse(channelUrl);
            String channelId = channelUri.getQueryParameter("channel");

            return channelId;
        }
        return "";
    }

    public static String parseYoutubeVideoId(String youtubeUrl) {
        String video_id = null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 &&
                youtubeUrl.startsWith("http")) {
            // ^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/
            String expression = "^.*((youtu.be" + "\\/)"
                    + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                // Regular expression some how doesn't work with id with "v" at
                // prefix
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11)
                    video_id = groupIndex1;
                else if (groupIndex1 != null && groupIndex1.length() == 10)
                    video_id = "v" + groupIndex1;
            }
        }
        return video_id;
    }
}
