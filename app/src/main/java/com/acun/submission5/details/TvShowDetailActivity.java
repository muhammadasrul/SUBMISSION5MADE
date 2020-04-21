package com.acun.submission5.details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.acun.submission5.R;
import com.acun.submission5.db.TvShowHelper;
import com.acun.submission5.models.TvShow;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_DATE;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_ID;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_LANGUAGE;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_OVERVIEW;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_POPULARITY;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_POSTER;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_SCORE;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_TITLE;
import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_URI;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvTitle, tvRelease, tvPopularity, tvScore, tvLanguage, tvOverview;
    private ImageView imgPoster;
    private FloatingActionButton fabFavAdd, fabFavDel;
    private TvShow tvShow;

    private TvShowHelper tvShowHelper;

    public static final String EXTRA_TVSHOW = "extra_tvshow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        assert tvShow != null;
        String tvShowId = Integer.toString(tvShow.getId());
        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
        tvShowHelper.open();

        tvTitle = findViewById(R.id.txt_tvshow_title_detail);
        tvRelease = findViewById(R.id.txt_tvshow_release_detail);
        tvScore = findViewById(R.id.txt_tvshow_userscore_detail);
        tvPopularity = findViewById(R.id.txt_tvshow_popularity_detail);
        tvLanguage = findViewById(R.id.txt_tvshow_language);
        tvOverview = findViewById(R.id.txt_tvshow_overview);
        imgPoster = findViewById(R.id.img_tvshow_detail);

        fabFavAdd = findViewById(R.id.fab_tvfav_add);
        fabFavDel = findViewById(R.id.fab_tvfav_del);
        fabFavAdd.setOnClickListener(this);
        fabFavDel.setOnClickListener(this);

        ProgressBar progressBar = findViewById(R.id.td_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        if (tvShowHelper.checkTvShow(tvShowId)){
            fabFavAdd.setVisibility(View.GONE);
            fabFavDel.setVisibility(View.VISIBLE);
        }

        getTvShow();

        progressBar.setVisibility(View.GONE);

    }

    private void getTvShow() {
        tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

        String tvshow_detail = getResources().getString(R.string.tv_show_detail);
        setTitle(tvshow_detail);

        tvTitle.setText(tvShow.getTitle());
        tvRelease.setText(tvShow.getRelease());
        tvScore.setText(tvShow.getScore()+ "/10");
        tvPopularity.setText(tvShow.getPopularity());
        tvLanguage.setText(tvShow.getLanguage());
        tvOverview.setText(tvShow.getOverview());

        Glide.with(TvShowDetailActivity.this)
                .load(tvShow.getPoster())
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(imgPoster);
    }

    public void addToFav() {
        ContentValues values = new ContentValues();
        values.put(TVSHOW_ID, tvShow.getId());
        values.put(TVSHOW_TITLE, tvShow.getTitle());
        values.put(TVSHOW_DATE, tvShow.getRelease());
        values.put(TVSHOW_POPULARITY, tvShow.getPopularity());
        values.put(TVSHOW_SCORE, tvShow.getScore());
        values.put(TVSHOW_LANGUAGE, tvShow.getLanguage());
        values.put(TVSHOW_OVERVIEW, tvShow.getOverview());
        values.put(TVSHOW_POSTER, tvShow.getPoster());

        getContentResolver().insert(TVSHOW_URI, values);
    }

    public void deleteFromFav() {
        getContentResolver().delete(Uri.parse(TVSHOW_URI + "/" +tvShow.getId()), null, null);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_tvfav_add) {
            String toastAdd = getString(R.string.addedtofav);

            addToFav();

            fabFavAdd.setVisibility(View.GONE);
            fabFavDel.setVisibility(View.VISIBLE);

            Toast.makeText(TvShowDetailActivity.this, toastAdd, Toast.LENGTH_SHORT).show();

        } else if (view.getId() == R.id.fab_tvfav_del) {
            String toastDel = getString(R.string.deletedfromfav);

            deleteFromFav();

            fabFavDel.setVisibility(View.GONE);
            fabFavAdd.setVisibility(View.VISIBLE);

            Toast.makeText(TvShowDetailActivity.this, toastDel, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvShowHelper.close();
    }
}
