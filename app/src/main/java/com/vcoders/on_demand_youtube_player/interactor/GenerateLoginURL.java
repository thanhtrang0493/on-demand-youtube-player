package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;

import io.reactivex.Observable;
import retrofit2.Response;

public class GenerateLoginURL extends InteractorYoutube<String> {
    public GenerateLoginURL(Context context) {
        super(context);
    }

    @Override
    protected Observable<Response<String>> buildObservable() {
        return getYoutubeService().generateLoginURL(body);
    }

    public GenerateLoginURL execute() {
        setBaseURL(Constant.BASE_URL_ACCOUNT);

        body.put("redirect_uri", Constant.REDIRECT_URI);
        body.put("response_type", "code");
        body.put("client_id", Constant.CLIENT_ID);
        body.put("scope", "https://www.googleapis.com/auth/analytics.readonly+https://www.googleapis.com/auth/userinfo.email");
        body.put("approval_prompt", "force");
        body.put("access_type", "offline");

        run();
        return this;
    }

    public GenerateLoginURL onListener(RequestAPIListener<String> listener) {
        setRequestAPIListener(listener);
        return this;
    }
}
