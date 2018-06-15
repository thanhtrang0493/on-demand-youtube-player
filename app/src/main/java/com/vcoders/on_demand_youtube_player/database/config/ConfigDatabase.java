package com.vcoders.on_demand_youtube_player.database.config;

import android.net.Uri;

public class ConfigDatabase {
    public static final String URI_MATCHER_DIR = "vnd.android.cursor.dir/vnd.";
    public static final String URI_MATCHER_ITEM = "vnd.android.cursor.item/vnd.";

    public static final int ALL_TOPIC = 1;
    public static final int SELECT_TOPIC = 2;
    public static final String NAME_URI_TABLE_TOPIC = "topic";

    // authority is the symbolic name of your provider
    // To avoid conflicts with other providers, you should use
    // Internet domain ownership (in reverse) as the basis of your provider authority.
    public static final String AUTHORITY = "com.vcoders.on_demand_youtube_player.provider";

    // create content URIs from the authority by appending path to database table
    public static final Uri CONTENT_URI_TABLE_TOPIC =
            Uri.parse("content://" + AUTHORITY + "/" + NAME_URI_TABLE_TOPIC);
}
