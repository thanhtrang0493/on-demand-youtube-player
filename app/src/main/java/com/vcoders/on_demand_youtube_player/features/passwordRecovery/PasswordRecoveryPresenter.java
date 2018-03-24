package com.vcoders.on_demand_youtube_player.features.passwordRecovery;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;

import javax.inject.Inject;


public class PasswordRecoveryPresenter extends BasePresenter<PasswordRecoveryView, PasswordRecoveryRouter> {

    Context context;

    @Inject
    public PasswordRecoveryPresenter(Context context) {
        this.context = context;
    }
}
