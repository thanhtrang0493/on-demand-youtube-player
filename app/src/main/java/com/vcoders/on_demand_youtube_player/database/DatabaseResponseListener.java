package com.vcoders.on_demand_youtube_player.database;

public interface DatabaseResponseListener<Result> {
    void onDatabaseResponse(Result response);

    void onDatabaseError();
}
