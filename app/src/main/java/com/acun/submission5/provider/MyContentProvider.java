package com.acun.submission5.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.acun.submission5.db.MovieHelper;
import com.acun.submission5.db.TvShowHelper;

import java.util.Objects;

import static com.acun.submission5.db.DatabaseContract.AUTHORITY;
import static com.acun.submission5.db.DatabaseContract.FavMovieColumns.MOVIE_TABLE_NAME;
import static com.acun.submission5.db.DatabaseContract.FavMovieColumns.MOVIE_URI;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_TABLE_NAME;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_URI;

public class MyContentProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

    private static final int TVSHOW = 3;
    private static final int TVSHOW_ID = 4;

    private TvShowHelper tvShowHelper;
    private MovieHelper movieHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        // content://com.acun.submission5/favorite_movie
        sUriMatcher.addURI(AUTHORITY, MOVIE_TABLE_NAME, MOVIE);

        // content://com.acun.submission5/favorite_movie/id
        sUriMatcher.addURI(AUTHORITY, MOVIE_TABLE_NAME + "/#", MOVIE_ID);
    }

    static {
        sUriMatcher.addURI(AUTHORITY, TVSHOW_TABLE_NAME, TVSHOW);
        sUriMatcher.addURI(AUTHORITY, TVSHOW_TABLE_NAME + "/#", TVSHOW_ID);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = movieHelper.deleteById(uri.getLastPathSegment());
                break;
            case TVSHOW_ID:
                deleted = tvShowHelper.deleteById(uri.getLastPathSegment());
                break;
                default:
                    deleted = 0;
                    break;
        }

        if (deleted > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        Uri contentUri = null;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insert(values);
                if (added > 0) {
                    contentUri = ContentUris.withAppendedId(MOVIE_URI, added);
                }
                break;
            case TVSHOW:
                added = tvShowHelper.insert(values);
                if (added > 0) {
                    contentUri = ContentUris.withAppendedId(TVSHOW_URI, added);
                }
                break;
                default:
                    added = 0;
                    break;
        }

        if (added > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return contentUri;
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();
        tvShowHelper = TvShowHelper.getInstance(getContext());
        tvShowHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryAllProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryById(uri.getLastPathSegment());
                break;
            case TVSHOW:
                cursor = tvShowHelper.queryAllProvider();
                break;
            case TVSHOW_ID:
                cursor = tvShowHelper.queryById(uri.getLastPathSegment());
                break;
                default:
                    cursor = null;
                    break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
