package com.example.movieappmvvm.ui.MainActivity;

import com.example.movieappmvvm.pojo.Movie;
import com.example.movieappmvvm.pojo.TimelineItem;
import com.example.movieappmvvm.repositories.MovieDataSource;
import com.example.movieappmvvm.repositories.NetworkState;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainPagedListRepository {
    LiveData<PagedList<TimelineItem>> mLivePagedList;
    MainDataSourceFactory mainFactory;
    private static MainPagedListRepository instance;
    public static MainPagedListRepository getInstance()
    {
        if (instance != null)
            return instance;
        return instance = new MainPagedListRepository();
    }
    public LiveData<PagedList<TimelineItem>> fetchLivePagedList(CompositeDisposable compositeDisposable)
    {
        mainFactory = new MainDataSourceFactory(compositeDisposable);
        PagedList.Config config = new PagedList
                .Config
                .Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.POST_PER_Page)
                .build();
        mLivePagedList = new LivePagedListBuilder(mainFactory,config).build();
        return mLivePagedList;
    }

    public LiveData<NetworkState> getRepoNetworkState()  {
//        return Transformations.switchMap(mainFactory.getmLiveDataMovie(), new Function<MovieDataSource, LiveData<NetworkState>>() {
//            @Override
//            public LiveData<NetworkState> apply(MovieDataSource input) {
//                return input.getNetworkStateMutableLiveData();
//            }
//        });
        return Transformations.switchMap(mainFactory.getmLiveDataMovie(), new Function<MovieDataSource, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(MovieDataSource input) {
                return input.getNetworkStateMutableLiveData();
            }
        });
    }
}
