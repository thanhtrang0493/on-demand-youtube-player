package com.vcoders.on_demand_youtube_player.interactor;


import android.content.Context;

import com.android.volley.VolleyError;
import com.vcoders.on_demand_youtube_player.architecture.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.architecture.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.architecture.RequestAPIResponse;
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

    private RequestAPIListener<List<VideoYoutube>> listener;

    private String playlistId;

    private RequestAPIResponse<List<VideoYoutube>> requestAPIResponse = new RequestAPIResponse<>();

    public GetVideoFromPlaylist getVideoFromPlaylist(Context context, String playlistId) {
        this.playlistId = playlistId;

        request(context);

        return this;
    }

    public GetVideoFromPlaylist onResponse(RequestAPIListener<List<VideoYoutube>> listener) {
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
                videoYoutube.setThumbnails(url);
                videoYoutube.setId(idVideo);
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
            requestAPIResponse.setErrorCode(error.networkResponse.statusCode);
            requestAPIResponse.setErrorMessage(error.getMessage());
            listener.onResponse(requestAPIResponse);
        }
    }

    @Override
    public void response(JSONObject response) {
        if (listener != null) {
            List<VideoYoutube> videoYoutubes = readJsonResponse(response);
            requestAPIResponse.setData(videoYoutubes);
            listener.onResponse(requestAPIResponse);
        }
    }
}
