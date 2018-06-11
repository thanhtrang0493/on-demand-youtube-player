package com.vcoders.on_demand_youtube_player.features.menu;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.features.login.LoginActivity;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import javax.inject.Inject;

public class MenuRouter extends BaseRouter {

    @Inject
    public MenuRouter(Context context) {
        super(context);
    }

    public void toLogin(){
        Utils.getInstance().changeActivity(context, LoginActivity.class);
    }
}
