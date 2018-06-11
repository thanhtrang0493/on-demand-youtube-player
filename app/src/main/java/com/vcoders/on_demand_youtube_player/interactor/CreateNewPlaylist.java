package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.google.gson.JsonObject;
import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;

import io.reactivex.Observable;
import retrofit2.Response;

public class CreateNewPlaylist extends InteractorYoutube<Data<String>> {

    private JsonObject objResource = new JsonObject();

    public CreateNewPlaylist(Context context) {
        super(context);
    }

    @Override
    protected Observable<Response<Data<String>>> buildObservable() {
        return getYoutubeService().createNewPlaylist(body, objResource);
    }

    public CreateNewPlaylist execute(String title) {
        setHeader(true);
        body.put("part", "snippet,status");

        JsonObject objSnippet = new JsonObject();
        objSnippet.addProperty("title", title);
        objSnippet.addProperty("description", "");

        JsonObject objStatus = new JsonObject();
        objStatus.addProperty("privacyStatus", "private");

        objResource.add("snippet", objSnippet);
        objResource.add("status", objStatus);

        run();
        return this;
    }

    public CreateNewPlaylist onListener(RequestAPIListener<Data<String>> listener) {
        setRequestAPIListener(listener);

        return this;
    }
}

//add video into playlist
//
//         "snippet": {
//                 "playlistId": "PL8hD12HFC-nuswc21_e64aPAy9B25sEH7",
//                 "resourceId": {
//                 "kind": "youtube#video",
//                 "videoId": "KMGuyGY5gvY"
//                 }
//                 }