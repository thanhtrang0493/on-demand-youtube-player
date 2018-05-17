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
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
