package com.vcoders.on_demand_youtube_player.interactor;


import android.content.Context;

import com.android.volley.VolleyError;
import com.vcoders.on_demand_youtube_player.architecture.InteractorYoutube1;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;
import com.vcoders.on_demand_youtube_player.services.YoutubeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetVideoFromPlaylist2 extends InteractorYoutube1 {
    private static final GetVideoFromPlaylist2 ourInstance = new GetVideoFromPlaylist2();

    private Context context;

    public GetVideoFromPlaylist2() {
    }

    public static GetVideoFromPlaylist2 getInstance() {
        return ourInstance;
    }

    private RequestAPIListener<List<VideoYoutube>> listener;

    private String playlistId;

    private ResponseAPIListener<List<VideoYoutube>> responseAPIListener = new ResponseAPIListener<>();

    public GetVideoFromPlaylist2 getVideoFromPlaylist(Context context, String playlistId) {
        this.playlistId = playlistId;

        request(context);

        return this;
    }

    public GetVideoFromPlaylist2 onResponse(RequestAPIListener<List<VideoYoutube>> listener) {
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
            responseAPIListener.setErrorCode(error.networkResponse.statusCode);
            responseAPIListener.setErrorMessage(error.getMessage());
            listener.onResponse(responseAPIListener);
        }
    }

    @Override
    public void response(JSONObject response) {
        if (listener != null) {
            List<VideoYoutube> videoYoutubes = readJsonResponse(response);
            responseAPIListener.setData(videoYoutubes);
            listener.onResponse(responseAPIListener);
        }
    }
}
