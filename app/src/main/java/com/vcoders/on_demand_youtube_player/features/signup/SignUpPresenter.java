package com.vcoders.on_demand_youtube_player.features.signup;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;

import javax.inject.Inject;


public class SignUpPresenter extends BasePresenter<SignUpView, SignUpRouter> {

    Context context;

    @Inject
    public SignUpPresenter(Context context) {
        this.context = context;
    }
}
