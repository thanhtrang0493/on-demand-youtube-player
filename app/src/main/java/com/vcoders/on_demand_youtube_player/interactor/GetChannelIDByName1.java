package com.vcoders.on_demand_youtube_player.interactor;


import android.content.Context;

import com.android.volley.VolleyError;
import com.vcoders.on_demand_youtube_player.architecture.InteractorYoutube1;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;
import com.vcoders.on_demand_youtube_player.model.Channel;
import com.vcoders.on_demand_youtube_player.services.YoutubeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetChannelIDByName1 extends InteractorYoutube1 {
    private static final GetChannelIDByName1 ourInstance = new GetChannelIDByName1();

    public static GetChannelIDByName1 getInstance() {
        return ourInstance;
    }

    String name;
    Context context;
    RequestAPIListener<List<Channel>> listener;
    ResponseAPIListener<List<Channel>> responseAPIListener;

    public GetChannelIDByName1 getChannelIDByName(Context context, String name) {
        this.context = context;
        this.name = name;
        responseAPIListener = new ResponseAPIListener<>();

        request(context);
        return this;
    }

    public GetChannelIDByName1 onResponse(RequestAPIListener<List<Channel>> listener) {
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
            responseAPIListener.setErrorCode(error.networkResponse.statusCode);
            responseAPIListener.setErrorMessage(error.getMessage());
            listener.onResponse(responseAPIListener);
        }
    }

    @Override
    public void response(JSONObject response) {
        if (listener != null) {
            List<Channel> channelList = readJsonResponse(response);
            responseAPIListener.setData(channelList);
            listener.onResponse(responseAPIListener);
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
