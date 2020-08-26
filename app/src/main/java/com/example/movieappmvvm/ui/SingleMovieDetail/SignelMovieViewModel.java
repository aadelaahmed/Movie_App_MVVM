package com.example.movieappmvvm.ui.SingleMovieDetail;

import android.app.Application;

import com.example.movieappmvvm.pojo.MovieDetails;
import com.example.movieappmvvm.repositories.NetworkState;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SignelMovieViewModel extends ViewModel {
    private SingleMovieRepository repository;
    CompositeDisposable compositeDisposable;
    public SignelMovieViewModel(int movieId) {
        compositeDisposable = new CompositeDisposable();
        repository = SingleMovieRepository.getInstance(compositeDisposable,movieId);
    }

    public LiveData<MovieDetails> getMovieDetails()
    {
        return repository.fetchMovieDetailsRepo();
    }

    public LiveData<NetworkState> getNetworkState()
    {
        return repository.fetchNetworkStateRepo();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
