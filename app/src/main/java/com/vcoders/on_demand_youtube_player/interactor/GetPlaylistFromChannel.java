package com.vcoders.on_demand_youtube_player.interactor;


import android.content.Context;

import com.android.volley.VolleyError;
import com.vcoders.on_demand_youtube_player.architecture.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.architecture.YoutubePlayerListener;
import com.vcoders.on_demand_youtube_player.architecture.YoutubePlayerResponse;
import com.vcoders.on_demand_youtube_player.model.Channel;
import com.vcoders.on_demand_youtube_player.services.YoutubeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetPlaylistFromChannel extends InteractorYoutube {

    private Context context;
    private String channelId;
    private YoutubePlayerListener<List<Channel>> listener;
    private YoutubePlayerResponse<List<Channel>> youtubePlayerResponse = new YoutubePlayerResponse<>();

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

    public GetPlaylistFromChannel onResponse(YoutubePlayerListener<List<Channel>> listener) {
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
            youtubePlayerResponse.setErrorCode(error.networkResponse.statusCode);
            youtubePlayerResponse.setErrorMessage(error.getMessage());
            listener.onResponse(youtubePlayerResponse);
        }
    }

    @Override
    public void response(JSONObject response) {
        if (listener != null) {
            List<Channel> channels = readJsonResponse(response);
            youtubePlayerResponse.setData(channels);
            listener.onResponse(youtubePlayerResponse);
        }
    }

    private List<Channel> readJsonResponse(JSONObject response) {
        List<Channel> channels = new ArrayList<>();
        try {
            JSONArray jsonItems = response.getJSONArray("items");
            String playlistId = "";
            String thumbnails = "";
            String title = "";
            String description = "";
            for (int i = 0; i < jsonItems.length(); i++) {
                JSONObject jsonItem = jsonItems.getJSONObject(i);
                playlistId = jsonItem.getString("id");
                JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails");
                JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                thumbnails = jsonMedium.getString("url");
                title = jsonSnippet.getString("title");
                description = jsonSnippet.getString("description");

                Channel channel = new Channel();
                channel.setPlaylistId(playlistId);
                channel.setThumbnails(thumbnails);
                channel.setTitle(title);
                channel.setDescription(description);
                channels.add(channel);
            }
        } catch (JSONException e) {

        }
        return channels;
    }
}
