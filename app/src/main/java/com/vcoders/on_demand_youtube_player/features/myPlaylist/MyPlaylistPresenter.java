package com.vcoders.on_demand_youtube_player.features.myPlaylist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.util.Log;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.interactor.GenerateLoginURL;
import com.vcoders.on_demand_youtube_player.interactor.GetMyPlaylist;
import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MyPlaylistPresenter extends BasePresenter<MyPlaylistView, MyPlaylistRouter> {

    Context context;

    @Inject
    public MyPlaylistPresenter(Context context) {
        this.context = context;
    }

    public List<PlayList> getListPlayListRecently(){
        List<PlayList> playListRecentlies=new ArrayList<>();
        playListRecentlies.add(new PlayList());
        playListRecentlies.add(new PlayList());
        playListRecentlies.add(new PlayList());
        playListRecentlies.add(new PlayList());
        return playListRecentlies;
    }

    public List<Video> getListPlayList(){
        List<Video> playLists=new ArrayList<>();
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        return playLists;
    }

    public void getMyPlaylist(){
        startServiceConfig();
//        new GenerateLoginURL(context).execute()
//                .onListener(new RequestAPIListener<String>() {
//                    @Override
//                    public void onResponse(ResponseAPIListener<String> response) {
//                        String t="";
//                    }
//                });
//        new GetMyPlaylist(context).execute()
//                .onListener(new RequestAPIListener<Data<Video>>() {
//                    @Override
//                    public void onResponse(ResponseAPIListener<Data<Video>> response) {
//                        String d="";
//                    }
//                });
    }

    public AuthState authState;
    public String userInfoUrl;
    public AuthorizationService authService;

    private void startServiceConfig() {
        AppAuthConfiguration.Builder builder = new AppAuthConfiguration.Builder();
        authService = new AuthorizationService(context, builder.build());

        AuthorizationServiceConfiguration serviceConfig =
                new AuthorizationServiceConfiguration(
                        Uri.parse("https://accounts.google.com/o/oauth2/v2/auth"),
                        Uri.parse("https://www.googleapis.com/oauth2/v4/token"));
        authState = new AuthState(serviceConfig);
        userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        startUserAuth();
    }

    private void startUserAuth() {
//        loginListener.onEvent(AuthRepo.this, AUTH_USER_AUTH_START);

        // may need to do this off UI thread?

        AuthorizationRequest.Builder authRequestBuilder = new AuthorizationRequest.Builder(
                authState.getAuthorizationServiceConfiguration(),
                Constant.CLIENT_ID,
                ResponseTypeValues.CODE,
                Uri.parse(Constant.REDIRECT_URI))
                .setScope("https://www.googleapis.com/auth/youtube");
        AuthorizationRequest authRequest = authRequestBuilder.build();

        CustomTabsIntent.Builder intentBuilder =
                authService.createCustomTabsIntentBuilder(authRequest.toUri());
        intentBuilder.setToolbarColor(context.getResources().getColor(R.color.colorPrimary));
        CustomTabsIntent authIntent = intentBuilder.build();

        Intent intent = authService.getAuthorizationRequestIntent(authRequest, authIntent);
        ((Activity)context).startActivityForResult(intent, 200);
//        loginListener.onUserAgentRequest(AuthRepo.this, intent);
    }
}
