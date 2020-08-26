package com.example.movieappmvvm.adapter.MainActivityList;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movieappmvvm.R;
import com.example.movieappmvvm.pojo.TimelineItem;
import com.example.movieappmvvm.repositories.NetworkState;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NetworkStateViewHolder extends BaseViewHolder {
    ProgressBar progress_bar_item;
    TextView error_msg_item;

    public NetworkStateViewHolder(@NonNull View itemView) {
        super(itemView);
        progress_bar_item = itemView.findViewById(R.id.progress_bar_item);
        error_msg_item = itemView.findViewById(R.id.error_msg_item);

    }

    @Override
    public void setData(TimelineItem timelineItem, Context context) {
        NetworkState networkState = timelineItem.getNetworkState();
        if (networkState != null && networkState == NetworkState.LOADING) {
            progress_bar_item.setVisibility(View.VISIBLE);

        } else {
            progress_bar_item.setVisibility(View.GONE);

        }

        if (networkState != null && networkState == NetworkState.ERROR) {
            error_msg_item.setVisibility(View.VISIBLE);
            error_msg_item.setText(networkState.getMsg());
        } else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
            error_msg_item.setVisibility(View.VISIBLE);
            error_msg_item.setText(networkState.getMsg());
        } else {
            error_msg_item.setVisibility(View.GONE);

        }
    }
}
