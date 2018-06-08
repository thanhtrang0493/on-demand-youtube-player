package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.model.User;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;

import io.reactivex.Observable;
import retrofit2.Response;

public class GetUserProfile extends InteractorYoutube<User> {
    public GetUserProfile(Context context) {
        super(context);
    }

    @Override
    protected Observable<Response<User>> buildObservable() {
        return getYoutubeService().getUserInfo();
    }

    public GetUserProfile execute() {
        setBaseURL(Constant.BASE_URL_ACCOUNT);
        setHeader(true);
        run();
        return this;
    }

    public GetUserProfile onListener(RequestAPIListener<User> listener) {
        setRequestAPIListener(listener);
        return this;
    }
}
