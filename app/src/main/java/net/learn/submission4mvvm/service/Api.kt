package net.learn.submission4mvvm.service

import net.learn.submission4mvvm.BuildConfig
import net.learn.submission4mvvm.model.detail.Detail
import net.learn.submission4mvvm.objectdata.MovieObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    //  https://api.themoviedb.org/3/discover/movie?api_key=925c18bd71917db0242931c2fce8c338&language=en-US&page=1
    @GET("discover/{type}?")
    fun getListItem(
        @Path("type") type: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Call<MovieObject>

    //    https://api.themoviedb.org/3/search/movie?api_key=925c18bd71917db0242931c2fce8c338&language=en-US&query=spiderman&page=1
    @GET("search/{type}")
    fun getSearch(
        @Path("type") type: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("query") query: String,
        @Query("page") page: Int
    ): Call<MovieObject>

    //  https://api.themoviedb.org/3/movie/181812?api_key=925c18bd71917db0242931c2fce8c338&language=en-US
    @GET("/movie/{movie_id}?")
    fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US"
    ): Call<Detail>

    //https://api.themoviedb.org/3/discover/movie?api_key=925c18bd71917db0242931c2fce8c338&language=en-US&page=1&primary_release_date.gte=2020-01-20&primary_release_date.lte=2020-01-20
    @GET("discover/movie")
    fun getReleaseToday(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("primary_release_date.gte") firstDate: String,
        @Query("primary_release_date.lte") lastDate: String
    ): Call<MovieObject>

}