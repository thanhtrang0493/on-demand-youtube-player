package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.android.volley.VolleyError;
import com.vcoders.on_demand_youtube_player.architecture.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;
import com.vcoders.on_demand_youtube_player.services.YoutubeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchVideoByName extends InteractorYoutube {

    private static final SearchVideoByName ourInstance = new SearchVideoByName();

    public static SearchVideoByName getInstance() {
        return ourInstance;
    }

    Context context;
    String name;
    ResponseAPIListener<List<VideoYoutube>> responseAPIListener;
    RequestAPIListener<List<VideoYoutube>> listener;

    public SearchVideoByName searchVideoByName(Context context, String name) {
        this.context = context;
        this.name = name;
        responseAPIListener = new ResponseAPIListener<>();

        request(context);
        return this;
    }

    public SearchVideoByName response(RequestAPIListener<List<VideoYoutube>> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public String setUrlYoutubeService() {
        return YoutubeAPI.getInstance().urlSearchVideoByName(name);
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
            List<VideoYoutube> videos = readJsonResponse(response);
            responseAPIListener.setData(videos);
            listener.onResponse(responseAPIListener);
        }
    }

    private List<VideoYoutube> readJsonResponse(JSONObject response) {
        List<VideoYoutube> videos = new ArrayList<>();
        try {
            JSONArray jsonItems = response.getJSONArray("items");

            String id = "";
            String channelId = "";
            String thumbnails = "";
            String channelTitle = "";
            String description = "";
            String publishedAt = "";
            String viewCount = "";
            String title = "";
            String time = "";
            for (int i = 0; i < jsonItems.length(); i++) {
                JSONObject jsonItem = jsonItems.getJSONObject(i);

                JSONObject jsonId = jsonItem.getJSONObject("id");
                id = jsonId.getString("videoId");

                JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                publishedAt = jsonSnippet.getString("publishedAt");
                channelId = jsonSnippet.getString("channelId");
                title = jsonSnippet.getString("title");
                description = jsonSnippet.getString("description");

                JSONObject jsonThumbnails = jsonSnippet.getJSONObject("thumbnails");
                JSONObject jsonMedium = jsonThumbnails.getJSONObject("medium");
                thumbnails = jsonMedium.getString("url");

                channelTitle = jsonSnippet.getString("channelTitle");

//                time
//                viewCount

                VideoYoutube video = new VideoYoutube();
                video.setId(id);
                video.setChannelId(channelId);
                video.setThumbnails(thumbnails);
                video.setChannelTitle(channelTitle);
                video.setDescription(description);
                video.setPublishedAt(publishedAt);
                video.setViewCount(viewCount);
                video.setTitle(title);
                video.setTime(time);
                videos.add(video);
            }
        } catch (JSONException e) {

        }
        return videos;
    }
}
