package com.vcoders.on_demand_youtube_player.database.tables.topic;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.database.DatabaseResponseListener;
import com.vcoders.on_demand_youtube_player.database.config.ConfigDatabase;
import com.vcoders.on_demand_youtube_player.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private DatabaseResponseListener listener;

    public TopicLoader(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                TopicColumns._ID,
                TopicColumns.TOPIC_ID,
                TopicColumns.NAME};
        CursorLoader cursorLoader = new CursorLoader(context,
                ConfigDatabase.CONTENT_URI_TABLE_TOPIC, projection,
                null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<Topic> results = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String rowId =
                            cursor.getString(cursor.getColumnIndexOrThrow(TopicColumns._ID));
                    Topic topic = new Topic();
                    topic.setId(cursor.getString(cursor.getColumnIndexOrThrow(TopicColumns.TOPIC_ID)));
                    topic.setName(cursor.getString(cursor.getColumnIndexOrThrow(TopicColumns.NAME)));
                    results.add(topic);
                } while (cursor.moveToNext());
            }
        }

        if (listener != null) {
            listener.onDatabaseResponse(results);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void getListTopicLoader(DatabaseResponseListener listener) {
        this.listener = listener;
        ((Activity) context).getLoaderManager().initLoader(0, null, this);
    }

    public Uri addTopicLoader(Topic topic) {
        if (topic != null) {
            ContentValues values = new ContentValues();
            values.put(TopicColumns.NAME, topic.getName());
            values.put(TopicColumns.TOPIC_ID, topic.getId());
            return context.getContentResolver().insert(ConfigDatabase.CONTENT_URI_TABLE_TOPIC, values);
        }
        return null;
    }

    public int updateTopicLoader(Topic topic, String idTableTopic) {
        if (topic != null) {
            ContentValues values = new ContentValues();
            values.put(TopicColumns.NAME, topic.getName());
            values.put(TopicColumns.TOPIC_ID, topic.getId());

            Uri uri = Uri.parse(ConfigDatabase.CONTENT_URI_TABLE_TOPIC + "/" + idTableTopic);

            return context.getContentResolver().update(uri, values, null, null);
        }
        return -1;
    }

    public int deleteTopicLoader(String idTableTopic) {
        Uri uri = Uri.parse(ConfigDatabase.CONTENT_URI_TABLE_TOPIC + "/" + idTableTopic);
        return context.getContentResolver().delete(uri, null, null);
    }
}
