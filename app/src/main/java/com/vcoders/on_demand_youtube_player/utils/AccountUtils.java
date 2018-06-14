package com.vcoders.on_demand_youtube_player.utils;

public class AccountUtils {
    public static AccountUtils ourInstance = new AccountUtils();

    public static AccountUtils getInstance() {
        return ourInstance;
    }

    public AccountUtils() {
    }

    private String email;
    private boolean isLogin;
    private String token;
    private String packageName;
    private String signature;

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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
