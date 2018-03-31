package com.vcoders.on_demand_youtube_player.features.searchVideos;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.architecture.RequestAPIResponse;
import com.vcoders.on_demand_youtube_player.interactor.SearchSuggestName;
import com.vcoders.on_demand_youtube_player.interactor.SearchVideoByName;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.List;

import javax.inject.Inject;


public class SearchVideosPresenter extends BasePresenter<SearchVideosView, SearchVideosRouter> {

    Context context;

    @Inject
    public SearchVideosPresenter(Context context) {
        this.context = context;
    }

    public void toDisplayListVideo(List<VideoYoutube> listVideo, String searchName) {
        getRouter().toListVideoFragment(listVideo, searchName);
    }

    public void searchSuggestName(String name) {
        SearchSuggestName searchSuggestName = new SearchSuggestName(context);
        searchSuggestName.searchSuggestName(context, name)
                .response(new RequestAPIListener<List<String>>() {
                    @Override
                    public void onResponse(RequestAPIResponse<List<String>> response) {
                        if (response.getErrorMessage() == null) {
                            getView().searchSuggestNameSuccess(response.getData());
                        } else
                            getView().showError(response.getErrorMessage());
                    }
                });
    }

    public void searchVideoByName(final String name) {
        getView().showLoading(true);

        SearchVideoByName.getInstance().searchVideoByName(context, name)
                .response(new RequestAPIListener<List<VideoYoutube>>() {
                    @Override
                    public void onResponse(RequestAPIResponse<List<VideoYoutube>> response) {
                        if (response.getErrorMessage() == null) {
                            getView().searchVideoSuccess(response.getData(), name);
                        } else
                            getView().showError(response.getErrorMessage());

                        getView().showLoading(false);
                    }
                });
    }
}
