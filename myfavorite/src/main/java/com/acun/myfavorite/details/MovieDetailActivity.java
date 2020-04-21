package com.acun.myfavorite.details;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.acun.myfavorite.R;
import com.acun.myfavorite.db.MovieHelper;
import com.acun.myfavorite.models.Movie;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.acun.myfavorite.db.DatabaseContract.FavMovieColumns.MOVIE_DATE;
import static com.acun.myfavorite.db.DatabaseContract.FavMovieColumns.MOVIE_ID;
import static com.acun.myfavorite.db.DatabaseContract.FavMovieColumns.MOVIE_LANGUAGE;
import static com.acun.myfavorite.db.DatabaseContract.FavMovieColumns.MOVIE_OVERVIEW;
import static com.acun.myfavorite.db.DatabaseContract.FavMovieColumns.MOVIE_POPULARITY;
import static com.acun.myfavorite.db.DatabaseContract.FavMovieColumns.MOVIE_POSTER;
import static com.acun.myfavorite.db.DatabaseContract.FavMovieColumns.MOVIE_SCORE;
import static com.acun.myfavorite.db.DatabaseContract.FavMovieColumns.MOVIE_TITLE;
import static com.acun.myfavorite.db.DatabaseContract.FavMovieColumns.MOVIE_URI;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle, tvRelease, tvPopularity, tvUserscore, tvLanguage, tvOverview;
    private ImageView imgPoster;
    private FloatingActionButton fabFavAdd, fabFavDel;
    private Movie movie;

    private MovieHelper movieHelper;

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        tvTitle = findViewById(R.id.txt_movie_title_detail);
        tvRelease = findViewById(R.id.txt_movie_release_detail);
        tvPopularity = findViewById(R.id.txt_movie_popularity_detail);
        tvUserscore = findViewById(R.id.txt_movie_userscore_detail);
        tvLanguage = findViewById(R.id.txt_movie_language);
        tvOverview = findViewById(R.id.txt_movie_overview);
        imgPoster = findViewById(R.id.img_movie_detail);

        fabFavAdd = findViewById(R.id.fab_fav_add);
        fabFavDel = findViewById(R.id.fab_fav_del);
        fabFavDel.setOnClickListener(this);

        ProgressBar progressBar = findViewById(R.id.md_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        fabFavDel.setVisibility(View.VISIBLE);

        getMovie();

        progressBar.setVisibility(View.GONE);

    }

    public void getMovie() {
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        String movieDetail = String.format(getResources().getString(R.string.movie_detail));
        setTitle(movieDetail);
        tvTitle.setText(movie.getTitle());
        tvRelease.setText(movie.getRelease());
        tvPopularity.setText(movie.getPopularity());
        tvUserscore.setText(movie.getScore()+"/10");
        tvLanguage.setText(movie.getLanguage());
        tvOverview.setText(movie.getOverview());
        Glide.with(MovieDetailActivity.this)
                .load(movie.getPoster())
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(imgPoster);
    }

    public void deleteFromFav() {
        getContentResolver().delete(Uri.parse(MOVIE_URI + "/" +movie.getId()), null, null);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_fav_del) {

            String toastDel = getString(R.string.deletedfromfav);

            deleteFromFav();

            fabFavDel.setVisibility(View.GONE);
            fabFavAdd.setVisibility(View.VISIBLE);

            Toast.makeText(MovieDetailActivity.this, toastDel, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
