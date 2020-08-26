package com.example.movieappmvvm.ui.MainActivity;

import com.example.movieappmvvm.pojo.Movie;
import com.example.movieappmvvm.repositories.MovieDataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    CompositeDisposable compositeDisposable;
    MovieDataSource movieDataSource;
    MutableLiveData<MovieDataSource> mLiveDataMovie;
    public MainDataSourceFactory(CompositeDisposable compositeDisposable)
    {
        this.compositeDisposable = compositeDisposable;
    }

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        movieDataSource = new MovieDataSource(compositeDisposable);
        mLiveDataMovie = new MutableLiveData<>();
        mLiveDataMovie.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getmLiveDataMovie() {
        return mLiveDataMovie;
    }
}
