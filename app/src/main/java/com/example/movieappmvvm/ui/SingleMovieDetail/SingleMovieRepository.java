package com.example.movieappmvvm.ui.SingleMovieDetail;

import com.example.movieappmvvm.repositories.MovieDetailDataSource;
import com.example.movieappmvvm.pojo.MovieDetails;
import com.example.movieappmvvm.repositories.NetworkState;

import androidx.lifecycle.LiveData;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SingleMovieRepository {
    static SingleMovieRepository instance;
    static MovieDetailDataSource dataSource;
    static int movieId;
    public static SingleMovieRepository getInstance(CompositeDisposable compositeDisposable,int tempId)
    {
        if (instance != null)
        {
            return instance;
        }
     dataSource = new MovieDetailDataSource(compositeDisposable);
     instance = new SingleMovieRepository();
     movieId = tempId;
     return instance;
    }
//    public void fillLiveData(int movieId)
//    {
//        dataSource.fetchMovieDetails(movieId);
//    }
    public LiveData<MovieDetails> fetchMovieDetailsRepo()
    {
        return dataSource.getMovieDetailsMutableLiveData(movieId);
    }

    public LiveData<NetworkState> fetchNetworkStateRepo()
    {
        return dataSource.getNetworkStateMutableLiveData(movieId);
    }

}
