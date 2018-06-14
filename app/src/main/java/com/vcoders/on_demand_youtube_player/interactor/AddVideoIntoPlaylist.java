package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.google.gson.JsonObject;
import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;

import io.reactivex.Observable;
import retrofit2.Response;

public class AddVideoIntoPlaylist extends InteractorYoutube<Data<String>> {

    private JsonObject objResource = new JsonObject();

    public AddVideoIntoPlaylist(Context context) {
        super(context);
    }

    @Override
    protected Observable<Response<Data<String>>> buildObservable() {
        return getYoutubeService().addVideoIntoPlaylist(body, objResource);
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
    public AddVideoIntoPlaylist execute(String playlistId, String videoId) {
        setHeader(true);
        body.put("part", "snippet");

        JsonObject objResource = new JsonObject();
        objResource.addProperty("kind", "youtube#video");
        objResource.addProperty("videoId", videoId);

        JsonObject objSnippet = new JsonObject();
        objSnippet.addProperty("playlistId", playlistId);
        objSnippet.add("resourceId", objResource);

        objResource.add("snippet", objSnippet);

        run();
        return this;
    }

    public AddVideoIntoPlaylist onListener(RequestAPIListener<Data<String>> listener) {
        setRequestAPIListener(listener);

        return this;
    }
}
