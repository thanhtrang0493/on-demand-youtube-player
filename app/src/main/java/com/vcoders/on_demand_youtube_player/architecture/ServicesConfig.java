package com.vcoders.on_demand_youtube_player.architecture;


public class ServicesConfig {

    private static final ServicesConfig ourInstance = new ServicesConfig();

    public static ServicesConfig getInstance() {
        return ourInstance;
    }

    public String DOMAIN = "http://suggestqueries.google.com/";
    public String AUTHORIZATION = "";

    public String getDOMAIN() {
        return DOMAIN;
    }

    public void setDOMAIN(String DOMAIN) {
        this.DOMAIN = DOMAIN;
    }

    public String getAUTHORIZATION() {
        return AUTHORIZATION;
    }

    public void setAUTHORIZATION(String AUTHORIZATION) {
        this.AUTHORIZATION = AUTHORIZATION;
    }
}
