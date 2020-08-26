package com.example.movieappmvvm.data;

import com.example.movieappmvvm.pojo.MovieDetails;
import com.example.movieappmvvm.pojo.MovieResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiMovieInterface {
    // https://api.themoviedb.org/3/movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=6e63c2317fbe963d76c3bdc2b785f6d1
    // https://api.themoviedb.org/3/

    @GET("movie/{movie_id}")
    Single<MovieDetails> getMovieDetails(@Path("movie_id") Integer movieId);

    @GET("movie/popular")
    Single<MovieResponse> getPopularMovie(@Query("page") int pageNum);

}
