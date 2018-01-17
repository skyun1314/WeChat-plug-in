package com.oppo.cdo.update.domain.dto;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by zk on 2018/1/16.
 */

public class wg extends ContentProvider {
    private SQLiteOpenHelper a;
    private boolean b = true;
    private ContentResolver contentResolver;
    public String table_name="pkgMd5";
    @Override
    public boolean onCreate() {
        this.a = new wi(getContext());
        contentResolver = getContext(). getContentResolver();
        a.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
            SQLiteDatabase database = a.getReadableDatabase();
            cursor = database.query(table_name, projection, selection, selectionArgs, null, null, sortOrder);
            cursor.setNotificationUri(contentResolver,uri);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int id =0;
            SQLiteDatabase database = a.getWritableDatabase();
            id= database.delete(table_name, selection, selectionArgs);
            contentResolver.notifyChange(uri,null);
        return id;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri u = null;
            SQLiteDatabase database = a.getWritableDatabase();

            long d = database.insert(table_name, "_id", values);
            u = ContentUris.withAppendedId(uri,d);
            contentResolver.notifyChange(u,null);
        return u;

    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}