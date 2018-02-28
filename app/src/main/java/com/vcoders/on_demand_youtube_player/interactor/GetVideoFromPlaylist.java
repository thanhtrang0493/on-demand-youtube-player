package com.vcoders.on_demand_youtube_player.interactor;


import android.content.Context;

import com.android.volley.VolleyError;
import com.vcoders.on_demand_youtube_player.architecture.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.architecture.YoutubePlayerListener;
import com.vcoders.on_demand_youtube_player.architecture.YoutubePlayerResponse;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;
import com.vcoders.on_demand_youtube_player.services.YoutubeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetVideoFromPlaylist extends InteractorYoutube {
    private static final GetVideoFromPlaylist ourInstance = new GetVideoFromPlaylist();

    private Context context;

    public GetVideoFromPlaylist() {
    }

    public static GetVideoFromPlaylist getInstance() {
        return ourInstance;
    }

    private YoutubePlayerListener<List<VideoYoutube>> listener;

    private String playlistId;

    private YoutubePlayerResponse<List<VideoYoutube>> youtubePlayerResponse = new YoutubePlayerResponse<>();

    public GetVideoFromPlaylist getVideoFromPlaylist(Context context, String playlistId) {
        this.playlistId = playlistId;

        request(context);

        return this;
    }

    public GetVideoFromPlaylist onResponse(YoutubePlayerListener<List<VideoYoutube>> listener) {
        this.listener = listener;
        return this;
    }

    private List<VideoYoutube> readJsonResponse(JSONObject response) {
        List<VideoYoutube> videoYoutubes = new ArrayList<>();
        try {
            JSONArray jsonItems = response.getJSONArray("items");

            for (int i = 0; i < jsonItems.length(); i++) {
                String title = "";
                String url = "";
                String idVideo = "";
                JSONObject jsonItem = jsonItems.getJSONObject(i);

                JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                title = jsonSnippet.getString("title");
                JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails");
                JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                url = jsonMedium.getString("url");
                JSONObject jsonResourceId = jsonSnippet.getJSONObject("resourceId");
                idVideo = jsonResourceId.getString("videoId");

                VideoYoutube videoYoutube = new VideoYoutube();
                videoYoutube.setTitle(title);
                videoYoutube.setThumbnail(url);
                videoYoutube.setVideoId(idVideo);
                videoYoutubes.add(videoYoutube);
            }
        } catch (JSONException e) {

        }
        return videoYoutubes;
    }

    @Override
    public String setUrlYoutubeService() {
        return YoutubeAPI.getInstance().urlGetVideoFromPlaylistId(playlistId);
    }

    @Override
    public void error(VolleyError error) {
        if (listener != null) {
            youtubePlayerResponse.setErrorCode(error.networkResponse.statusCode);
            youtubePlayerResponse.setErrorMessage(error.getMessage());
            listener.onResponse(youtubePlayerResponse);
        }
    }

    @Override
    public void response(JSONObject response) {
        if (listener != null) {
            List<VideoYoutube> videoYoutubes = readJsonResponse(response);
            youtubePlayerResponse.setData(videoYoutubes);
            listener.onResponse(youtubePlayerResponse);
        }
    }
}
