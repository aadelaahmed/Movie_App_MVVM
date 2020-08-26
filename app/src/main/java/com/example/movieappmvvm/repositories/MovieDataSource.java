package com.example.movieappmvvm.repositories;

import android.util.Log;

import com.example.movieappmvvm.data.ApiMovieInterface;
import com.example.movieappmvvm.data.MovieApiClient;
import com.example.movieappmvvm.pojo.Movie;
import com.example.movieappmvvm.pojo.MovieDetails;
import com.example.movieappmvvm.pojo.MovieResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {
    private static final String TAG = "MovieDataSource";
    public static final int FIRST_PAGE = 1;
    public static final int POST_PER_Page = 20;
    CompositeDisposable compositeDisposable;
    ApiMovieInterface mApi;
    MutableLiveData<NetworkState> networkStateMutableLiveData = new MutableLiveData<>();

    public MovieDataSource(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
        mApi = MovieApiClient.getInstance();
    }


    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie> callback) {
        networkStateMutableLiveData.postValue(NetworkState.LOADING);
        compositeDisposable.add(
                mApi.getPopularMovie(FIRST_PAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new io.reactivex.rxjava3.observers.DisposableSingleObserver<MovieResponse>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull MovieResponse movieResponse) {
                                networkStateMutableLiveData.postValue(NetworkState.LOADED);
                                callback.onResult(movieResponse.getMovieList(), null, FIRST_PAGE + 1);
                                //used to enable pagelist representing the passed loaded data
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                networkStateMutableLiveData.postValue(NetworkState.ERROR);
                                Log.d(TAG, "onError: " + e.getLocalizedMessage());
                            }
                        })
        );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        //we can't create any logic here beacause of the recycler view provided with scrolling up
    }

    public MutableLiveData<NetworkState> getNetworkStateMutableLiveData() {
        return networkStateMutableLiveData;
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
//        compositeDisposable.add(
//                mApi.getPopularMovie(params.key+1)
//                .subscribeOn(Schedulers.io())
//                .subscribeWith(new io.reactivex.rxjava3.observers.DisposableSingleObserver<Movie>() {
//                    @Override
//                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Movie movie) {
//
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//
//                    }
//                })
//        );

                compositeDisposable.add(
                        mApi.getPopularMovie(params.key) //updated automatically
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new io.reactivex.rxjava3.observers.DisposableSingleObserver<MovieResponse>() {
                                    @Override
                                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull MovieResponse movieResponse) {
                                        networkStateMutableLiveData.postValue(NetworkState.LOADED);
                                        if (movieResponse.getTotalPages() > params.key) {
                                            callback.onResult(movieResponse.getMovieList(), params.key + 1);
                                            networkStateMutableLiveData.postValue(NetworkState.LOADED);
                                        } else {
                                            networkStateMutableLiveData.postValue(NetworkState.ENDOFLIST);
                                        }
                                        //movieDetailsMutableLiveData.postValue(movieDetails);
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                        networkStateMutableLiveData.postValue(NetworkState.ERROR);
                                        Log.d(TAG, "onError: " + e.getLocalizedMessage());
                                    }
                                })
                );
    }
}
