package com.example.movieappmvvm.adapter.MainActivityList;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieappmvvm.R;
import com.example.movieappmvvm.pojo.Movie;
import com.example.movieappmvvm.pojo.TimelineItem;
import com.example.movieappmvvm.ui.SingleMovieDetail.SingleMovie;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.movieappmvvm.data.MovieApiClient.POSTER_BASE_URL;

public class MovieItemViewHolder extends BaseViewHolder {
    TextView cv_movie_title,cv_movie_release_date;
    ImageView cv_iv_movie_poster;
    public MovieItemViewHolder(@NonNull View itemView) {
        super(itemView);
        cv_movie_title = itemView.findViewById(R.id.cv_movie_title);
        cv_movie_release_date = itemView.findViewById(R.id.cv_movie_release_date);
        cv_iv_movie_poster = itemView.findViewById(R.id.cv_iv_movie_poster);

    }

    @Override
    public void setData(TimelineItem timelineItem, final Context context) {
        //binding data into ui
        final Movie movie = timelineItem.getMovie();
        cv_movie_title.setText(movie.getTitle());
        cv_movie_release_date.setText(movie.getReleaseDate());
        String moviePosterURL = POSTER_BASE_URL + movie.getPosterPath();
        Glide.with(context)
                .load(moviePosterURL)
                .into(cv_iv_movie_poster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleMovie.class);
                intent.putExtra("id", movie.getId());
                context.startActivity(intent);
            }
        });
    }
}
