package com.vcoders.on_demand_youtube_player.model;


import java.io.Serializable;

public class Topic implements Serializable {
    private String id;
    private String name;
    private boolean isSelect;

    public Topic() {
    }

    public Topic(String id, String name) {
        this.id = id;
        this.name = name;
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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
