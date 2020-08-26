package com.example.movieappmvvm.ui.SingleMovieDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieappmvvm.databinding.ActivitySingleMovieBinding;
import com.example.movieappmvvm.pojo.MovieDetails;
import com.example.movieappmvvm.repositories.NetworkState;
import com.example.movieappmvvm.ui.MainActivity.MainActivity;

import java.text.NumberFormat;
import java.util.Locale;

import static com.example.movieappmvvm.data.MovieApiClient.POSTER_BASE_URL;

public class SingleMovie extends AppCompatActivity {
    ActivitySingleMovieBinding binding;
    SignelMovieViewModel singleViewModel;
    ProgressBar progressBar;
    TextView textError,movie_title,movie_release_date,movie_tagline,movie_rating,movie_runtime,movie_overview
            ,movie_budget,movie_revenue;
    ImageView iv_movie_poster;
    int receivedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySingleMovieBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        progressBar = binding.progressBar;
        textError = binding.txtError;
        movie_title = binding.movieTitle;
        movie_release_date = binding.movieReleaseDate;
        movie_tagline = binding.movieTagline;
        movie_rating = binding.movieRating;
        movie_runtime = binding.movieRuntime;
        movie_overview = binding.movieOverview;
        movie_budget = binding.movieBudget;
        movie_revenue = binding.movieRevenue;
        iv_movie_poster = binding.ivMoviePoster;
        receivedId = getIntent().getIntExtra(MainActivity.ID_KEY,1);
        singleViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignelMovieViewModel(receivedId);
            }
        }).get(SignelMovieViewModel.class);

        singleViewModel.getMovieDetails().observe(this, new Observer<MovieDetails>() {
            @Override
            public void onChanged(MovieDetails movieDetails) {
                bindUI(movieDetails);
            }
        });

        singleViewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                if (networkState == NetworkState.LOADING)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    textError.setVisibility(View.INVISIBLE);
                }
                else if (networkState == NetworkState.ERROR)
                {

                    textError.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void bindUI(MovieDetails movieDetails) {

        movie_title.setText(movieDetails.getTitle());
        movie_tagline.setText(movieDetails.getTagline());
        movie_release_date.setText(movieDetails.getReleaseDate());
        movie_rating.setText(movieDetails.getRating().toString());
        movie_runtime.setText(movieDetails.getRuntime());
        movie_overview.setText(movieDetails.getOverview());


        NumberFormat formatCurrency = NumberFormat.getCurrencyInstance(Locale.US);
        movie_budget.setText(formatCurrency.format(movieDetails.getBudget()));
        movie_revenue.setText(formatCurrency.format(movieDetails.getRevenue()));

        String moviePosterURL = POSTER_BASE_URL + movieDetails.getPosterPath();
        Glide.with(this)
                .load(moviePosterURL)
                .into(iv_movie_poster);
    }
}