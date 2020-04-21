package com.acun.submission5.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.acun.submission5.R;
import com.acun.submission5.details.MovieDetailActivity;
import com.acun.submission5.models.Movie;
import com.bumptech.glide.Glide;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder> {

    private Cursor movieListCursor;

    public FavMovieAdapter(Context context) {
        Context mContext = context;
    }


    public void  setMovieListCursor(Cursor listCursor) {
        this.movieListCursor = listCursor;
    }
    @NonNull
    @Override
    public FavMovieAdapter.FavMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_view_item, parent, false);
        return new FavMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavMovieAdapter.FavMovieViewHolder holder, int position) {
        final Movie movie = getItem(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvRelease.setText(movie.getRelease());
        holder.tvUserscore.setText(movie.getScore()+"/10");
        holder.tvPopularity.setText(movie.getPopularity());

        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(holder.imgPoster);
        holder.cvMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    private Movie getItem(int position) {
        if (!movieListCursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new Movie(movieListCursor);
    }

    @Override
    public int getItemCount() {
        if (movieListCursor == null) return 0;
        return movieListCursor.getCount();
    }

    class FavMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvRelease, tvUserscore, tvPopularity;
        CardView cvMovie;

        private FavMovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRelease = itemView.findViewById(R.id.tv_release);
            tvUserscore = itemView.findViewById(R.id.tv_user_score);
            tvPopularity = itemView.findViewById(R.id.tv_popularity);
            cvMovie = itemView.findViewById(R.id.cvMovie);
        }
    }
}
