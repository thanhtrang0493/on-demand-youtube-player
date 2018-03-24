package com.vcoders.on_demand_youtube_player.features.login;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;

import javax.inject.Inject;


public class LoginPresenter extends BasePresenter<LoginView, LoginRouter> {

    Context context;

    @Inject
    public LoginPresenter(Context context) {
        this.context = context;
    }
}
