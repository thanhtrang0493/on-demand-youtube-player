package com.vcoders.on_demand_youtube_player.services;


import com.vcoders.on_demand_youtube_player.utils.AccountUtils;
import com.vcoders.on_demand_youtube_player.utils.Utils;


public class YoutubeAPI {
    private static final YoutubeAPI ourInstance = new YoutubeAPI();

    public static YoutubeAPI getInstance() {
        return ourInstance;
    }

    public static String API_KEY = "AIzaSyBENTkoHFtO8tDHGC5Mcuzya9kT00kIusg";

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

    public String urlGetMyPlaylist(String token) {
//        return "https://www.googleapis.com/youtube/v3/playlists?part=snippet,contentDetails&mine=true&access_token=" +
//                token + "&key=" + API_KEY + "&maxResults=50";
        String token1 = AccountUtils.getInstance().getToken();
        return "https://accounts.google.com/o/oauth2/v2/auth?Authorization=Basic" +token1+
                "&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fyoutube.readonly&" +
                "response_type=code&" +
                "state=security_token%3D138r5719ru3e1%26url%3Dhttps://oauth2.example.com/token&" +
                "redirect_uri=com.example.app:/oauth2redirect&" +
                "client_id=" + token;
    }
}

