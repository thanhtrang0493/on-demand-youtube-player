package com.vcoders.on_demand_youtube_player.database.tables.topic;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TopicTable {

    private static final String LOG_TAG = TopicTable.class.getSimpleName();
    public static final String TOPIC_TABLE = "Topic";

    private static final String TABLE_TOPIC_CREATE =
            "CREATE TABLE if not exists " + TOPIC_TABLE + " (" +
                    TopicColumns._ID + " integer PRIMARY KEY autoincrement," +
                    TopicColumns.TOPIC_ID + "," +
                    TopicColumns.NAME + ");";

    public static void onCreate(SQLiteDatabase db) {
        Log.w(LOG_TAG, TABLE_TOPIC_CREATE);
        db.execSQL(TABLE_TOPIC_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TOPIC_TABLE);
        onCreate(db);
    }
}
