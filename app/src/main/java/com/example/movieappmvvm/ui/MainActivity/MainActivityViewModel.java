package com.example.movieappmvvm.ui.MainActivity;

import com.example.movieappmvvm.pojo.Movie;
import com.example.movieappmvvm.pojo.TimelineItem;
import com.example.movieappmvvm.repositories.NetworkState;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MainPagedListRepository repository;
    LiveData<PagedList<TimelineItem>> mTempMovieLiveData;
    public LiveData<PagedList<TimelineItem>> getMainPagedList()
    {
        repository = MainPagedListRepository.getInstance();
        mTempMovieLiveData = repository.fetchLivePagedList(compositeDisposable);
        return mTempMovieLiveData;
    }

    public LiveData<NetworkState> getMainNetworkState()
    {
        return repository.getRepoNetworkState();
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public boolean listIsEmpty()
    {
        return (mTempMovieLiveData.getValue().isEmpty()) ? true : false;
    }
}
