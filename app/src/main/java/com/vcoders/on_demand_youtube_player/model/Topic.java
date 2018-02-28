package com.vcoders.on_demand_youtube_player.model;


public class Topic {
    private String id;
    private String name;

    public Topic(){}

    public Topic(String name){
        this.name=name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
