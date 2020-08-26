package com.example.movieappmvvm.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    int page;
    @SerializedName("results")
    List<Movie> movieList;
    @SerializedName("total_pages")
    int totalPages;
    @SerializedName("total_results")
    int totalResults;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
