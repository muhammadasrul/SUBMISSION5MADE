package com.acun.submission5.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import com.acun.submission5.R;
import com.acun.submission5.fragments.SearchTvShowFragment;

import java.util.Objects;

public class SearchTvShowActivity extends AppCompatActivity {

    public static String TVSHOW_NAME ="tvshow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv_show);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String searchTvShow = getResources().getString(R.string.search_tvshow);
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) (menu.findItem(R.id.action_search)).getActionView();
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconified(false);
            searchView.setQueryHint(searchTvShow);
            searchView.onActionViewExpanded();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String title) {
                    Bundle data = new Bundle();
                    data.putString(TVSHOW_NAME, title);
                    SearchTvShowFragment fragment = new SearchTvShowFragment();
                    fragment.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_result, fragment).commit();

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }
}
