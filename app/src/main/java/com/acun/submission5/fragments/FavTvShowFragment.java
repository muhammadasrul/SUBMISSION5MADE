package com.acun.submission5.fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.acun.submission5.R;
import com.acun.submission5.adapters.FavTvShowAdapter;
import com.acun.submission5.db.TvShowHelper;

import java.util.Objects;

import static com.acun.submission5.db.DatabaseContract.FavTvShowColumns.TVSHOW_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvShowFragment extends Fragment{

    private FavTvShowAdapter adapter;
    private ProgressBar progressBar;
    private TvShowHelper tvShowHelper;
    private RecyclerView rvFavTvShow;
    private Cursor cursorTvShowList;

    private static final String EXTRA_STATE = "extra_state";

    public FavTvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        rvFavTvShow = view.findViewById(R.id.rv_tvshow);
        rvFavTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavTvShow.setHasFixedSize(true);

        tvShowHelper = TvShowHelper.getInstance(getActivity());
        tvShowHelper.open();

        adapter = new FavTvShowAdapter(getActivity());
        rvFavTvShow.setAdapter(adapter);

        adapter.setTvShowListCursor(cursorTvShowList);
        new LoadTvShowAsync().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private class LoadTvShowAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            tvShowHelper.open();
            return Objects.requireNonNull(getContext()).getContentResolver().query(
                    TVSHOW_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor tvShowsCursor) {
            super.onPostExecute(tvShowsCursor);
            cursorTvShowList = tvShowsCursor;
            adapter.setTvShowListCursor(cursorTvShowList);
            adapter.notifyDataSetChanged();
        }
    }
}