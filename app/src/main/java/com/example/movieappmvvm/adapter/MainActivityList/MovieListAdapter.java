package com.example.movieappmvvm.adapter.MainActivityList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieappmvvm.R;
import com.example.movieappmvvm.adapter.MainActivityList.MovieItemViewHolder;
import com.example.movieappmvvm.pojo.Movie;
import com.example.movieappmvvm.pojo.TimelineItem;
import com.example.movieappmvvm.repositories.NetworkState;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListAdapter extends PagedListAdapter<TimelineItem, BaseViewHolder> {
    public static final int NETWORK_STATE_ITEM_TYPE = 1;
    public static final int MOVIE_ITEM_TYPE = 2;
    NetworkState networkState = null;
    Context mContext;
    public MovieListAdapter(Context mContext) {
        super(diffCallback);
        this.mContext = mContext;
    }

    static DiffUtil.ItemCallback<TimelineItem> diffCallback = new DiffUtil.ItemCallback<TimelineItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull TimelineItem oldItem, @NonNull TimelineItem newItem) {
            if (oldItem.getMovie() != null && newItem.getMovie() != null) {
                return oldItem.getMovie().getId() == newItem.getMovie().getId();
            }
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TimelineItem oldItem, @NonNull TimelineItem newItem) {
            if (oldItem.getMovie() != null && newItem.getMovie() != null) {
                return oldItem.getMovie().equals(newItem.getMovie());
            }
            return false;
        }
    };


    public boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED)
            return true;
        else
            return false;
    }


    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean hadExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean hasExtraRow = hasExtraRow();
        //hasExtraRow() method totally depends on network state
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount());   //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount());  //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(getItemCount() - 1);       //add the network message at the end
        }

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MOVIE_ITEM_TYPE)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_item,parent,false);
            return new MovieItemViewHolder(view);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.network_state_item,parent,false);
            return new NetworkStateViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setData(getItem(position),mContext);
    }


}
