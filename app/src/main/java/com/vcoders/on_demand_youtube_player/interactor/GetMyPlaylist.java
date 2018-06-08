package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;

import io.reactivex.Observable;
import retrofit2.Response;

public class GetMyPlaylist extends InteractorYoutube<Data<Video>> {
    public GetMyPlaylist(Context context) {
        super(context);
    }

    @Override
    protected Observable<Response<Data<Video>>> buildObservable() {
        return getYoutubeService().getMyPlaylist(body);
    }
// https://hackernoon.com/adding-oauth2-to-mobile-android-and-ios-clients-using-the-appauth-sdk-f8562f90ecff
// https://www.themarketingtechnologist.co/google-oauth-2-enable-your-application-to-access-data-from-a-google-user/
//  https://developers.google.com/oauthplayground/?code=4/AABikvBU_cIPFc_cvcW5TCgsfzyUf8IAwy8qLlsQrerwihu0cq6KYs8bmsYRGN6K-eD8rCQjou2qvJIhzCnG1g0#
    public GetMyPlaylist execute() {
        setHeader(true);

        body.put("part", "snippet,contentDetails");
        body.put("mine", "true");
        body.put("maxResults", "25");
//        body.put("onBehalfOfContentOwner", "");
//        body.put("onBehalfOfContentOwnerChannel", "");

        run();
        return this;
    }

    public GetMyPlaylist onListener(RequestAPIListener<Data<Video>> listener) {
        setRequestAPIListener(listener);

        return this;
    }
}
