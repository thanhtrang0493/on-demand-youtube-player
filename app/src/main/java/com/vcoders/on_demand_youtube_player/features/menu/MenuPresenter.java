package com.vcoders.on_demand_youtube_player.features.menu;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.auth.AuthEvent;
import com.vcoders.on_demand_youtube_player.auth.AuthException;
import com.vcoders.on_demand_youtube_player.auth.AuthLogoutListener;
import com.vcoders.on_demand_youtube_player.auth.AuthRepo;
import com.vcoders.on_demand_youtube_player.auth.YoutubeApp;
import com.vcoders.on_demand_youtube_player.interactor.GetUserProfile;
import com.vcoders.on_demand_youtube_player.model.User;
import com.vcoders.on_demand_youtube_player.utils.AccountUtils;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;

import javax.inject.Inject;

public class MenuPresenter extends BasePresenter<MenuView, MenuRouter> {

    private Context context;
    public YoutubeApp app;
    private AuthRepo authRepo;

    @Inject
    public MenuPresenter(Context context) {
        this.context = context;
        app = (YoutubeApp) context.getApplicationContext();
        authRepo = app.getAuthRepo();
    }

    public void toLogin(){
        getRouter().toLogin();
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

    public void logout() {
        authRepo.logout(logoutListener);
    }

    private final AuthLogoutListener logoutListener = new AuthLogoutListener() {
        public void onStart(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
        }

        public void onSuccess(AuthRepo repo, AuthEvent event) {
            AccountUtils.getInstance().setToken(null);
            AccountUtils.getInstance().setPackageName(null);
            AccountUtils.getInstance().setSignature(null);
            AccountUtils.getInstance().setLogin(false);

            getView().onLogoutSuccess();
        }

        public void onFailure(AuthRepo repo, AuthEvent event, AuthException ex) {
            String description = event.getDescription() + ": " + ex.getMessage();
            getView().showError(description);
        }
    };
}
