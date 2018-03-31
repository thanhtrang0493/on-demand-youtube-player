package com.vcoders.on_demand_youtube_player.features.menu;


import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;

public class MenuFragment extends BaseFragment<HomeComponent> {
    @Override
    protected void initializeView(Bundle savedInstanceState) {

    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_menu;
    }

    @Override
    protected String getTitleActionBar() {
        return null;
    }

    @Override
    protected TypeActionBar[] getTypeActionBar() {
        return new TypeActionBar[0];
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected BaseRouter getRouter() {
        return null;
    }

    @Override
    protected void inject() {

    }
}
