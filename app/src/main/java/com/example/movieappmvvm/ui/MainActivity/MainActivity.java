package com.example.movieappmvvm.ui.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movieappmvvm.adapter.MainActivityList.MovieListAdapter;
import com.example.movieappmvvm.databinding.ActivityMainBinding;
import com.example.movieappmvvm.pojo.Movie;
import com.example.movieappmvvm.pojo.TimelineItem;
import com.example.movieappmvvm.repositories.NetworkState;
import com.example.movieappmvvm.ui.SingleMovieDetail.SingleMovie;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static final String ID_KEY = "id";
    RecyclerView rvMovieList;
    MainActivityViewModel mainViewModel;
    MovieListAdapter adapter;
    ProgressBar progressBar;
    TextView textPopular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        rvMovieList = binding.rvMovieList;
        progressBar = binding.progressBarPopular;
        textPopular = binding.txtErrorPopular;
        adapter = new MovieListAdapter(this);
        mainViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        iniUI();
        mainViewModel.getMainPagedList().observe(this
                , timelineItems -> adapter.submitList(timelineItems));

        mainViewModel.getMainNetworkState().observe(this, networkState -> {
            if (mainViewModel.listIsEmpty() && networkState == NetworkState.LOADING)
                progressBar.setVisibility(View.VISIBLE);
            else
                progressBar.setVisibility(View.GONE);

            if (mainViewModel.listIsEmpty() && networkState == NetworkState.ERROR)
                textPopular.setVisibility(View.VISIBLE);
             else
                textPopular.setVisibility(View.GONE);

            if (!mainViewModel.listIsEmpty()) {
                adapter.setNetworkState(networkState);
            }
        });

    }

    private void iniUI() {
        GridLayoutManager grid = new GridLayoutManager(this, 3);
        grid.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == MovieListAdapter.NETWORK_STATE_ITEM_TYPE)
                    return 3;
                else
                    return 1;
            }
        });
        rvMovieList.setAdapter(adapter);
        rvMovieList.setLayoutManager(grid);
        rvMovieList.setHasFixedSize(true);
    }
}