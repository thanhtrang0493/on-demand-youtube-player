package com.vcoders.on_demand_youtube_player.features.setTimer;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;

import javax.inject.Inject;


public class SetTimerPresenter extends BasePresenter<SetTimerView, SetTimerRouter> {

    Context context;

    @Inject
    public SetTimerPresenter(Context context) {
        this.context = context;
    }
}
