package com.vcoders.on_demand_youtube_player.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vcoders.on_demand_youtube_player.database.tables.topic.TopicTable;

public class YoutubeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "YoutubePlayer";
    private static final int DATABASE_VERSION = 1;

    public YoutubeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        TopicTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        TopicTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

}
