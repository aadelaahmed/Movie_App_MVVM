package com.example.movieappmvvm.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Movie {
    int id;
    @SerializedName("poster_path")
    String posterPath;
    @SerializedName("release_date")
    String releaseDate;
    String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                Objects.equals(posterPath, movie.posterPath) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, posterPath, releaseDate, title);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
