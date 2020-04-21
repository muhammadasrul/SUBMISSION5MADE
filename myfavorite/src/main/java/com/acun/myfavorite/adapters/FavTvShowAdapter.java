package com.acun.myfavorite.adapters;

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

import com.acun.myfavorite.R;
import com.acun.myfavorite.details.TvShowDetailActivity;
import com.acun.myfavorite.models.TvShow;
import com.bumptech.glide.Glide;

public class FavTvShowAdapter extends RecyclerView.Adapter<FavTvShowAdapter.TvShowViewHolder> {

    private Cursor tvShowListCursor;

    public FavTvShowAdapter(Context context) {
        Context mContext = context;
    }

    public void setTvShowListCursor(Cursor listCursor) {
        this.tvShowListCursor = listCursor;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tvshow_card_view_item, parent, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowViewHolder holder, int position) {
        final TvShow tvShow = getItem(position);
        holder.tvTitle.setText(tvShow.getTitle());
        holder.tvRelease.setText(tvShow.getRelease());
        holder.tvScore.setText(tvShow.getScore()+"/10");
        holder.tvPopularity.setText(tvShow.getPopularity());

        Glide.with(holder.itemView.getContext())
                .load(tvShow.getPoster())
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(holder.imgPoster);
        holder.cvTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), TvShowDetailActivity.class);
                intent.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, tvShow);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    private TvShow getItem(int position) {
        if (!tvShowListCursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new TvShow(tvShowListCursor);
    }

    @Override
    public int getItemCount() {
        if (tvShowListCursor == null) return 0;
        return tvShowListCursor.getCount();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvRelease, tvScore, tvPopularity;
        ImageView imgPoster;
        CardView cvTvShow;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_show_title);
            tvRelease = itemView.findViewById(R.id.tv_show_release);
            tvScore = itemView.findViewById(R.id.tv_show_user_score);
            tvPopularity = itemView.findViewById(R.id.tv_show_popularity);
            imgPoster = itemView.findViewById(R.id.img_tvshow_poster);
            cvTvShow = itemView.findViewById(R.id.cvTvShow);
        }
    }
}
