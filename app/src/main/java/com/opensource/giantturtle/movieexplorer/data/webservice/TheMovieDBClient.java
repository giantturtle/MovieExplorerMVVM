package com.opensource.giantturtle.movieexplorer.data.webservice;


import com.opensource.giantturtle.movieexplorer.data.webservice.apiresponse.srchmovresponse.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TheMovieDBClient {
    String BASE_URL = "https://api.themoviedb.org/3/";
    String POSTER_PATH_BASE_URL = "https://image.tmdb.org/t/p/w342/";
    //lower quality
    //String POSTER_PATH_BASE_URL = "https://image.tmdb.org/t/p/w185/";



    @GET("search/movie")
    Call<MovieSearchResponse> searchMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET("search/multi")
    Call<MovieSearchResponse> searchMulti(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieSearchResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieSearchResponse> getPopularMovies(@Query("api_key") String apiKey);

    //TODO Implement movie details @GET("movie/{id}") Call<MovieResponse> movie(@Path("id") int id);
    /*@GET
    Call<MovieDetailsResponse> getMovieDetailsById(@Url String fullUrl);*/

    /**/


}
