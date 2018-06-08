package com.vcoders.on_demand_youtube_player.features.menu;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.interactor.GetUserProfile;
import com.vcoders.on_demand_youtube_player.model.User;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;

import javax.inject.Inject;

public class MenuPresenter extends BasePresenter<MenuView, MenuRouter> {

    private Context context;

    @Inject
    public MenuPresenter(Context context) {
        this.context = context;
    }

    public void getUserProfile() {
        getView().showLoading(true);
        new GetUserProfile(context).execute()
                .onListener(new RequestAPIListener<User>() {
                    @Override
                    public void onResponse(ResponseAPIListener<User> response) {
                        if (response.getErrorMessage() == null) {
                            getView().onUpdateUserInfo(response.getData());
                        } else
                            getView().showError(response.getErrorMessage());
                        getView().showLoading(false);
                    }
                });
    }
}
