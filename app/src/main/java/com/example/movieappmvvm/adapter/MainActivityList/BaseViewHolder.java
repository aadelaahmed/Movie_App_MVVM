package com.example.movieappmvvm.adapter.MainActivityList;

import android.content.Context;
import android.view.View;

import com.example.movieappmvvm.pojo.TimelineItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void setData(TimelineItem timelineItem, Context context);
}
