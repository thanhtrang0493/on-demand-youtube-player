package com.vcoders.on_demand_youtube_player.features.menu;

import com.vcoders.on_demand_youtube_player.architecture.BaseView;
import com.vcoders.on_demand_youtube_player.model.User;

public interface MenuView extends BaseView {
    void onUpdateUserInfo(User user);

    void onLogoutSuccess();
}
