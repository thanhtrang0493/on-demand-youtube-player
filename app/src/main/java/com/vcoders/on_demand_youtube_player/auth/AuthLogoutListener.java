package com.vcoders.on_demand_youtube_player.auth;

public interface AuthLogoutListener {
    default void onStart(AuthRepo repo, AuthEvent event) {}
    default void onSuccess(AuthRepo repo, AuthEvent event) {}
    default void onFailure(AuthRepo repo, AuthEvent event, AuthException ex) {}
}
