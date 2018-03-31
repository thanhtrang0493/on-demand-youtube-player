package com.vcoders.on_demand_youtube_player.services;


import com.vcoders.on_demand_youtube_player.utils.Utils;


public class YoutubeAPI {
    private static final YoutubeAPI ourInstance = new YoutubeAPI();

    public static YoutubeAPI getInstance() {
        return ourInstance;
    }

    public String API_KEY = "AIzaSyBENTkoHFtO8tDHGC5Mcuzya9kT00kIusg";

    private YoutubeAPI() {
    }

    public String urlGetVideoFromPlaylistId(String playlistId) {
        return "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="
                + playlistId + "&key=" + API_KEY + "&maxResults=50";
    }

    public String urlGetPlaylistFromChannelId(String channelId) {
        return "https://www.googleapis.com/youtube/v3/playlists?part=snippet,contentDetails&channelId=" +
                channelId + "&key=" + API_KEY + "&maxResults=50";
    }

    public String urlGetChannelIDByName(String name) {
        return "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + Utils.getInstance().includeSpaceIntoString(name) + "&type=channel" +
                "&key=" + API_KEY + "&maxResults=50";
    }

    public String urlSearchVideoByName(String name) {
        return "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" +
                Utils.getInstance().includeSpaceIntoString(name) + "&type=video&key=" + API_KEY + "&maxResults=50";
    }
}

