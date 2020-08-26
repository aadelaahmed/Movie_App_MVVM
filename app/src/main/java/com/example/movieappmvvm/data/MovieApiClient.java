package com.example.movieappmvvm.data;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class MovieApiClient {
    static ApiMovieInterface mApi;
    private static final String API_KEY = "6e63c2317fbe963d76c3bdc2b785f6d1";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";

// https://api.themoviedb.org/3/movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&page=1
// https://api.themoviedb.org/3/movie/299534?api_key=6e63c2317fbe963d76c3bdc2b785f6d1
// https://image.tmdb.org/t/p/w342/or06FN3Dka5tukK1e9sl16pB3iy.jpg

    public static ApiMovieInterface getInstance() {
        if (mApi != null)
        {
            return mApi;
        }
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl url = originalRequest
                        .url()
                        .newBuilder()
                        .addQueryParameter("api_key",API_KEY)
                        .build();
                Request resRequest = originalRequest
                        .newBuilder()
                        .url(url)
                        .build();
                return chain.proceed(resRequest);
            }
        };
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        mApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ApiMovieInterface.class);

        return mApi;
    }
}
