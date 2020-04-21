package com.acun.submission5.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_DATE;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_ID;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_LANGUAGE;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_OVERVIEW;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_POPULARITY;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_POSTER;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_SCORE;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_TITLE;
import static com.acun.submission5.db.DatabaseContract.getColumnInt;
import static com.acun.submission5.db.DatabaseContract.getColumnString;

public class TvShow implements Parcelable {
    private Integer id;
    private String title;
    private String release;
    private String score;
    private String popularity;
    private String language;
    private String overview;
    private String poster;

    private TvShow(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.release = in.readString();
        this.score = in.readString();
        this.popularity = in.readString();
        this.language = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public TvShow(Cursor cursor) {
        this.id = getColumnInt(cursor, TVSHOW_ID);
        this.title = getColumnString(cursor, TVSHOW_TITLE);
        this.release = getColumnString(cursor, TVSHOW_DATE);
        this.popularity = getColumnString(cursor, TVSHOW_POPULARITY);
        this.score = getColumnString(cursor, TVSHOW_SCORE);
        this.language = getColumnString(cursor, TVSHOW_LANGUAGE);
        this.overview = getColumnString(cursor, TVSHOW_OVERVIEW);
        this.poster = getColumnString(cursor, TVSHOW_POSTER);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public TvShow(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.title = object.getString("name");
            this.release = object.getString("first_air_date");
            this.score = object.getString("vote_average");
            this.popularity = object.getString("popularity");
            this.language = object.getString("original_language");
            this.overview = object.getString("overview");
            String poster_path = object.getString("poster_path");
            this.poster = "https://image.tmdb.org/t/p/w185"+poster_path;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.release);
        parcel.writeString(this.score);
        parcel.writeString(this.popularity);
        parcel.writeString(this.language);
        parcel.writeString(this.overview);
        parcel.writeString(this.poster);
    }
}
