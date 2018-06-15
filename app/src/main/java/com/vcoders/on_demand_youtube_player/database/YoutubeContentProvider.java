package com.vcoders.on_demand_youtube_player.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.vcoders.on_demand_youtube_player.database.config.ConfigDatabase;
import com.vcoders.on_demand_youtube_player.database.tables.topic.TopicColumns;
import com.vcoders.on_demand_youtube_player.database.tables.topic.TopicTable;

public class YoutubeContentProvider extends ContentProvider {

    private YoutubeDatabaseHelper dbHelper;

    // a content URI pattern matches content URIs using wildcard characters:
    // *: Matches a string of any valid characters of any length.
    // #: Matches a string of numeric characters of any length.
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ConfigDatabase.AUTHORITY, ConfigDatabase.NAME_URI_TABLE_TOPIC, ConfigDatabase.ALL_TOPIC);
        uriMatcher.addURI(ConfigDatabase.AUTHORITY, ConfigDatabase.NAME_URI_TABLE_TOPIC + "/#", ConfigDatabase.SELECT_TOPIC);
    }

    // system calls onCreate() when it starts up the provider.
    @Override
    public boolean onCreate() {
        // get access to the database helper
        dbHelper = new YoutubeDatabaseHelper(getContext());
        return false;
    }

    // The query() method must return a Cursor object, or if it fails,
    // throw an Exception. If you are using an SQLite database as your data storage,
    // you can simply return the Cursor returned by one of the query() methods of the
    // SQLiteDatabase class. If the query does not match any rows, you should return a
    // Cursor instance whose getCount() method returns 0. You should return null only
    // if an internal error occurred during the query process.
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TopicTable.TOPIC_TABLE);

        switch (uriMatcher.match(uri)) {
            case ConfigDatabase.ALL_TOPIC:
                //do nothing
                break;
            case ConfigDatabase.SELECT_TOPIC:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(TopicColumns._ID + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        return cursor;

    }

    //Return the MIME type corresponding to a content URI
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case ConfigDatabase.ALL_TOPIC:
                return ConfigDatabase.URI_MATCHER_DIR + ConfigDatabase.AUTHORITY + "." + ConfigDatabase.NAME_URI_TABLE_TOPIC;
            case ConfigDatabase.SELECT_TOPIC:
                return ConfigDatabase.URI_MATCHER_ITEM + ConfigDatabase.AUTHORITY + "." + ConfigDatabase.NAME_URI_TABLE_TOPIC;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    // The insert() method adds a new row to the appropriate table, using the values
    // in the ContentValues argument. If a column name is not in the ContentValues argument,
    // you may want to provide a default value for it either in your provider code or in
    // your database schema.
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ConfigDatabase.ALL_TOPIC:
                //do nothing
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        long id = db.insert(TopicTable.TOPIC_TABLE, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(ConfigDatabase.CONTENT_URI_TABLE_TOPIC + "/" + id);
    }

    // The delete() method deletes rows based on the seletion or if an id is
    // provided then it deleted a single row. The methods returns the numbers
    // of records delete from the database. If you choose not to delete the data
    // physically then just update a flag here.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ConfigDatabase.ALL_TOPIC:
                //do nothing
                break;
            case ConfigDatabase.SELECT_TOPIC:
                String id = uri.getPathSegments().get(1);
                selection = TopicColumns._ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = db.delete(TopicTable.TOPIC_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    // The update method() is same as delete() which updates multiple rows
    // based on the selection or a single row if the row id is provided. The
    // update method returns the number of updated rows.
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ConfigDatabase.ALL_TOPIC:
                //do nothing
                break;
            case ConfigDatabase.SELECT_TOPIC:
                String id = uri.getPathSegments().get(1);
                selection = TopicColumns._ID  + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = db.update(TopicTable.TOPIC_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }
}
