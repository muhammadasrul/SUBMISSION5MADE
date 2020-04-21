package com.acun.myfavorite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.acun.myfavorite.db.DatabaseContract.FavTvShowColumns.TVSHOW_ID;
import static com.acun.myfavorite.db.DatabaseContract.FavTvShowColumns.TVSHOW_TABLE_NAME;

public class TvShowHelper {

    private static final String DATABASE_TABLE = TVSHOW_TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static TvShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAllProvider() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, TVSHOW_ID + " = ?", new String[]{id});
    }

    public boolean checkTvShow(String id) {
        database = databaseHelper.getWritableDatabase();
        String select = "SELECT * FROM " + TVSHOW_TABLE_NAME + " WHERE " + TVSHOW_ID + " =?";
        Cursor cursor = database.rawQuery(select, new String[]{id});
        boolean checkTvShow = false;
        if (cursor.moveToFirst()) {
            checkTvShow = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkTvShow;
    }
}
