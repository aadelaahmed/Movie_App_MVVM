package com.example.movieappmvvm.repositories;

import android.util.Log;

import com.example.movieappmvvm.data.ApiMovieInterface;
import com.example.movieappmvvm.data.MovieApiClient;
import com.example.movieappmvvm.pojo.MovieDetails;
import com.example.movieappmvvm.repositories.NetworkState;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailDataSource {
    private static final String TAG = "MovieDetailDataSource";
    CompositeDisposable compositeDisposable;
    ApiMovieInterface mApi;
    MutableLiveData<NetworkState> networkStateMutableLiveData;
    MutableLiveData<MovieDetails> movieDetailsMutableLiveData;

    public MovieDetailDataSource(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }


    public LiveData<NetworkState> getNetworkStateMutableLiveData(int movieId) {
        if (networkStateMutableLiveData != null)
            return networkStateMutableLiveData;
        else {
            fetchMovieDetails(movieId);
            return networkStateMutableLiveData;
        }
    }

    public LiveData<MovieDetails> getMovieDetailsMutableLiveData(int movieId) {
        fetchMovieDetails(movieId);
        return movieDetailsMutableLiveData;
    }

    public void fetchMovieDetails(int movieId) {
        networkStateMutableLiveData = new MutableLiveData<>();
        movieDetailsMutableLiveData = new MutableLiveData<>();
        networkStateMutableLiveData.setValue(NetworkState.LOADING);
        mApi = MovieApiClient.getInstance();
        compositeDisposable.add(
                mApi.getMovieDetails(movieId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<MovieDetails>() {
                            @Override
                            public void onSuccess(@NonNull MovieDetails movieDetails) {
                                networkStateMutableLiveData.postValue(NetworkState.LOADED);
                                movieDetailsMutableLiveData.postValue(movieDetails);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                networkStateMutableLiveData.postValue(NetworkState.ERROR);
                                Log.d(TAG, "onError: " + e.getLocalizedMessage());
                            }
                        })
        );
    }
}
