package com.example.movieappmvvm.pojo;

import com.example.movieappmvvm.adapter.MainActivityList.MovieListAdapter;
import com.example.movieappmvvm.repositories.NetworkState;

public class TimelineItem {
    Movie movie;
    NetworkState networkState;
    int viewType;
    public TimelineItem(Movie movie)
    {
        this.movie = movie;
        this.viewType = MovieListAdapter.MOVIE_ITEM_TYPE;
    }

    public TimelineItem(NetworkState networkState)
    {
        this.networkState = networkState;
        this.viewType = MovieListAdapter.NETWORK_STATE_ITEM_TYPE;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public NetworkState getNetworkState() {
        return networkState;
    }

    public void setNetworkState(NetworkState networkState) {
        this.networkState = networkState;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
