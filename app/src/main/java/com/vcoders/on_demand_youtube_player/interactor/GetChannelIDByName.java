package com.vcoders.on_demand_youtube_player.interactor;


import android.content.Context;

import com.android.volley.VolleyError;
import com.vcoders.on_demand_youtube_player.architecture.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.architecture.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.architecture.RequestAPIResponse;
import com.vcoders.on_demand_youtube_player.model.Channel;
import com.vcoders.on_demand_youtube_player.services.YoutubeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetChannelIDByName extends InteractorYoutube {
    private static final GetChannelIDByName ourInstance = new GetChannelIDByName();

    public static GetChannelIDByName getInstance() {
        return ourInstance;
    }

    String name;
    Context context;
    RequestAPIListener<List<Channel>> listener;
    RequestAPIResponse<List<Channel>> requestAPIResponse;

    public GetChannelIDByName getChannelIDByName(Context context, String name) {
        this.context = context;
        this.name = name;
        requestAPIResponse = new RequestAPIResponse<>();

        request(context);
        return this;
    }

    public GetChannelIDByName onResponse(RequestAPIListener<List<Channel>> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public String setUrlYoutubeService() {
        return YoutubeAPI.getInstance().urlGetChannelIDByName(name);
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
            List<Channel> channelList = readJsonResponse(response);
            requestAPIResponse.setData(channelList);
            listener.onResponse(requestAPIResponse);
        }
    }

    private List<Channel> readJsonResponse(JSONObject response) {
        List<Channel> channelList = new ArrayList<>();
        try {
            JSONArray jsonItems = response.getJSONArray("items");

            for (int i = 0; i < jsonItems.length(); i++) {
                JSONObject jsonItem = jsonItems.getJSONObject(i);

                JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                String channelId = jsonSnippet.getString("channelId");
                String title = jsonSnippet.getString("title");

                JSONObject jsonThumbnails = jsonSnippet.getJSONObject("thumbnails");
                JSONObject jsonMedium = jsonThumbnails.getJSONObject("medium");
                String url = jsonMedium.getString("url");
            }
        } catch (JSONException e) {

        }
        return channelList;
    }
}
