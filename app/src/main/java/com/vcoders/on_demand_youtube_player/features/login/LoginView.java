package com.vcoders.on_demand_youtube_player.features.login;

import com.vcoders.on_demand_youtube_player.architecture.BaseView;


public interface LoginView extends BaseView {
    void onLoginSuccess();
    void onLoginFail(int errorCode,String errorMessage);
}
