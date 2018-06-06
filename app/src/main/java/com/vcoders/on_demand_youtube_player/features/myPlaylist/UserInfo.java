package com.vcoders.on_demand_youtube_player.features.myPlaylist;

public class UserInfo {
    private String lastName;
    private String firstName;
    private String imageLink;

    public UserInfo(String lastName, String firstName, String imageLink) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.imageLink = imageLink;
    }

    public String getfirstName() {
        return firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public String getImageLink() {
        return imageLink;
    }
}
