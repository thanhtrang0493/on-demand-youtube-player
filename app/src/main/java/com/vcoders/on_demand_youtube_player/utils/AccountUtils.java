package com.vcoders.on_demand_youtube_player.utils;

public class AccountUtils {
    private static final AccountUtils ourInstance = new AccountUtils();

    public static AccountUtils getInstance() {
        return ourInstance;
    }

    private AccountUtils() {
    }

    private String email;
    private boolean isLogin;
}
