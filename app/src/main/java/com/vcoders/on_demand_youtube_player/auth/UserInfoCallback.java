package com.vcoders.on_demand_youtube_player.auth;


interface UserInfoCallback {
    void call(UserInfo userInfo, AuthException ex);
}
