package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.Interactor;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


public class SearchSuggestName extends Interactor<List<Object>> {

    @Inject
    public SearchSuggestName(Context context) {
        super(context);
    }

    String name;
    Context context;
    RequestAPIListener<List<String>> listener;
    ResponseAPIListener<List<String>> responseAPIListener;

    public SearchSuggestName searchSuggestName(Context context, String name) {
        this.context = context;
        this.name = name;
        responseAPIListener = new ResponseAPIListener<>();

        run();
        return this;
    }

    public SearchSuggestName response(RequestAPIListener<List<String>> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected Observable<List<Object>> buildObservable() {
        Map<String, String> data = new HashMap<>();
        data.put("q", Utils.getInstance().includeSpaceIntoString(name));
        data.put("client", "firefox");
        data.put("hl", "fr");
        return getServicesAPI().urlSearchSuggestName(data);
    }

    @Override
    protected void response(List<Object> s) {
        responseAPIListener.setData(getListNameResponse(s));
        listener.onResponse(responseAPIListener);
    }

    private List<String> getListNameResponse(List<Object> s) {
        List<String> nameList = new ArrayList<>();
        try {
            nameList = (List<String>) s.get(1);
        }catch (Exception e){

        }
        return nameList;
    }

    @Override
    protected void error(int errorCode, String errorMessage) {
        responseAPIListener.setErrorCode(errorCode);
        responseAPIListener.setErrorMessage(errorMessage);
        listener.onResponse(responseAPIListener);
    }
}
