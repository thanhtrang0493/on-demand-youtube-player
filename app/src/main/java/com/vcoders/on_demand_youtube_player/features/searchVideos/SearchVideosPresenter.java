package com.vcoders.on_demand_youtube_player.features.searchVideos;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.interactor.SearchSuggestName;
import com.vcoders.on_demand_youtube_player.interactor.SearchVideoByName;
import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;

import java.util.List;

import javax.inject.Inject;


public class SearchVideosPresenter extends BasePresenter<SearchVideosView, SearchVideosRouter> {

    Context context;
    Data<Video> dataVideo;

    @Inject
    public SearchVideosPresenter(Context context) {
        this.context = context;
    }

    public void toDisplayListVideo(List<Video> listVideo, String searchName) {
        getRouter().toListVideoFragment(listVideo, searchName);
    }

    public void searchSuggestName(String name) {
        SearchSuggestName searchSuggestName = new SearchSuggestName(context);
        searchSuggestName.searchSuggestName(context, name)
                .response(new RequestAPIListener<List<String>>() {
                    @Override
                    public void onResponse(ResponseAPIListener<List<String>> response) {
                        if (response.getErrorMessage() == null) {
                            getView().searchSuggestNameSuccess(response.getData());
                        } else
                            getView().showError(response.getErrorMessage());
                    }
                });
    }

    public void searchVideoByName(final String name) {
        getView().showLoading(true);

        new SearchVideoByName(context).execute(name)
                .onListener(new RequestAPIListener<Data<Video>>() {
                    @Override
                    public void onResponse(ResponseAPIListener<Data<Video>> response) {
                        if (response.getErrorMessage() == null) {
                            dataVideo=response.getData();
                            getView().searchVideoSuccess(response.getData().getItems(), name);
                        } else
                            getView().showError(response.getErrorMessage());

                        getView().showLoading(false);
                    }
                });
    }
}
