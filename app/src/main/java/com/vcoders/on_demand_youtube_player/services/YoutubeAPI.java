package com.vcoders.on_demand_youtube_player.services;


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
        return "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=" +
                channelId + "&key=" + API_KEY + "&maxResults=50";
    }

    public String urlGetChannelIDByName(String name) {
        return "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + name + "&type=channel" +
                "&key=" + API_KEY + "&maxResults=50";
    }
}


//https://www.googleapis.com/youtube/v3/search?part=snippet&q=music&type=channel&key=AIzaSyBENTkoHFtO8tDHGC5Mcuzya9kT00kIusg&maxResults=50
//
//        https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UC-9-kyTW8ZkZNDHQJ6FgpwQ&key=AIzaSyBENTkoHFtO8tDHGC5Mcuzya9kT00kIusg&maxResults=50