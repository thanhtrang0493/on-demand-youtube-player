package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.android.volley.VolleyError;
import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.InteractorYoutube1;
import com.vcoders.on_demand_youtube_player.services.YoutubeAPI;

import org.json.JSONObject;

public class GetMyPlaylist1 extends InteractorYoutube1 {

    Context context;

    public GetMyPlaylist1(Context context){
        this.context= context;
    }

    public GetMyPlaylist1 getMyPlaylist() {
        request(context);
        return this;
    }

    @Override
    public String setUrlYoutubeService() {
//        String token = AccountUtils.getInstance().getToken();
        String token= context.getResources().getString(R.string.google_client_id);
        return YoutubeAPI.getInstance().urlGetMyPlaylist(token);
    }

    @Override
    public void error(VolleyError error) {
        String dd="";
    }

    @Override
    public void response(JSONObject response) {
        String dd="";

//        https://accounts.google.com/o/oauth2/v2/auth?scope=email%20profile&response_type=code&state=security_token%3D138r5719ru3e1%26url%3Dhttps://oauth2.example.com/token&redirect_uri=com.example.app:/oauth2redirect&client_id=client_id
    }
}
