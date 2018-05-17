package com.vcoders.on_demand_youtube_player.architecture;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;

import org.json.JSONObject;

import java.util.List;

public abstract class InteractorYoutube {

    public InteractorYoutube() {
    }

    public void request(Context context) {
        String url = setUrlYoutubeService();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final ResponseAPIListener<List<VideoYoutube>> responseAPIListener = new ResponseAPIListener<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                response(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error(error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public abstract String setUrlYoutubeService();

    public abstract void error(VolleyError error);

    public abstract void response(JSONObject response);
}
