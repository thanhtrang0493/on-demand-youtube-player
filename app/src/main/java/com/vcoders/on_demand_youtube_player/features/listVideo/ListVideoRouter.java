package com.vcoders.on_demand_youtube_player.features.listVideo;

import android.content.Context;
import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.features.login.LoginActivity;
import com.vcoders.on_demand_youtube_player.features.player.PlayerFragment;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;


public class ListVideoRouter extends BaseRouter {

    @Inject
    public ListVideoRouter(Context context) {
        super(context);
    }

    public void toPlayer(List<Video> videoYoutubes, int position) {
        if ((BaseActivity) context != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.VIDEOS, (Serializable) videoYoutubes);
            bundle.putSerializable(Constant.POSITION, position);

            ((BaseActivity) context).changeFragment(new PlayerFragment(), bundle);
        }
    }

    public void toLogin() {
        Utils.getInstance().changeActivity(context, LoginActivity.class);
    }
}
