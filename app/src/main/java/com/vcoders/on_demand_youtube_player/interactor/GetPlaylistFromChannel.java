package com.vcoders.on_demand_youtube_player.interactor;


import android.content.Context;

import com.android.volley.VolleyError;
import com.vcoders.on_demand_youtube_player.architecture.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.services.YoutubeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetPlaylistFromChannel extends InteractorYoutube {

    private Context context;
    private String channelId;
    private RequestAPIListener<List<PlayList>> listener;
    private ResponseAPIListener<List<PlayList>> responseAPIListener = new ResponseAPIListener<>();

    private static final GetPlaylistFromChannel ourInstance = new GetPlaylistFromChannel();

    public GetPlaylistFromChannel() {
    }

    public static GetPlaylistFromChannel getInstance() {
        return ourInstance;
    }

    public GetPlaylistFromChannel getPlaylistFromChannel(Context context, String channelId) {
        this.context = context;
        this.channelId = channelId;

        request(context);
        return this;
    }

    public GetPlaylistFromChannel onResponse(RequestAPIListener<List<PlayList>> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public String setUrlYoutubeService() {
        return YoutubeAPI.getInstance().urlGetPlaylistFromChannelId(channelId);
    }

    @Override
    public void error(VolleyError error) {
        if (listener != null) {
            responseAPIListener.setErrorCode(error != null ? error.networkResponse.statusCode : 0);
            responseAPIListener.setErrorMessage(error != null ? error.getMessage() : "");
            listener.onResponse(responseAPIListener);
        }
    }

    @Override
    public void response(JSONObject response) {
        if (listener != null) {
            List<PlayList> playLists = readJsonResponse(response);
            responseAPIListener.setData(playLists);
            listener.onResponse(responseAPIListener);
        }
    }

    private List<PlayList> readJsonResponse(JSONObject response) {
        List<PlayList> playLists = new ArrayList<>();
        try {
            JSONArray jsonItems = response.getJSONArray("items");
            String playlistId = "";
            String thumbnails = "";
            String title = "";
            String description = "";
            String videoCount = "";
            String localized = "";
            for (int i = 0; i < jsonItems.length(); i++) {
                JSONObject jsonItem = jsonItems.getJSONObject(i);
                playlistId = jsonItem.getString("id");
                JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails");
                JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                thumbnails = jsonMedium.getString("url");
                title = jsonSnippet.getString("title");
                description = jsonSnippet.getString("description");
                JSONObject jsonContentDetails = jsonItem.getJSONObject("contentDetails");
                videoCount = jsonContentDetails.getString("itemCount");
                JSONObject jsonLocalized = jsonSnippet.getJSONObject("localized");
                localized = jsonLocalized.getString("title");

                PlayList playList = new PlayList();
                playList.setChannelId(channelId);
                playList.setId(playlistId);
                playList.setThumbnails(thumbnails);
                playList.setTitle(title);
                playList.setDescription(description);
                playList.setVideoCount(videoCount);
                playList.setLocalized(localized);
                playLists.add(playList);
            }
        } catch (JSONException e) {

        }
        return playLists;
    }
}
