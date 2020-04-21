package com.acun.myfavorite.fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.acun.myfavorite.R;
import com.acun.myfavorite.adapters.FavMovieAdapter;
import com.acun.myfavorite.db.MovieHelper;

import java.util.Objects;

import static com.acun.myfavorite.db.DatabaseContract.FavMovieColumns.MOVIE_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment {

    private FavMovieAdapter adapter;
    private ProgressBar progressBar;
    private MovieHelper movieHelper;
    private RecyclerView rvFavMovie;
    private Cursor cursorMovieList;

    private static final String EXTRA_STATE = "extra_state";

    public FavMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        rvFavMovie = view.findViewById(R.id.rv_movie);
        rvFavMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavMovie.setHasFixedSize(true);

        movieHelper = MovieHelper.getInstance(getActivity());
        movieHelper.open();

        adapter = new FavMovieAdapter(getActivity());
        rvFavMovie.setAdapter(adapter);

        adapter.setMovieListCursor(cursorMovieList);
        new LoadMoviesAsync().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private class LoadMoviesAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext()).getContentResolver().query(
                    MOVIE_URI,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        protected void onPostExecute(Cursor moviesCursor) {
            super.onPostExecute(moviesCursor);
            cursorMovieList = moviesCursor;
            adapter.setMovieListCursor(cursorMovieList);
            adapter.notifyDataSetChanged();
        }
    }
}
