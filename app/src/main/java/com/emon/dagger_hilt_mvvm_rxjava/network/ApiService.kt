package com.emon.dagger_hilt_mvvm_rxjava.network

import com.emon.dagger_hilt_mvvm_rxjava.model.movie.MovieInfo
import com.emon.dagger_hilt_mvvm_rxjava.model.movie.MovieListResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("3/movie/top_rated")
     fun getMoviesList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") pageIndex: Int
    ):Single<MovieListResponse>

    @GET("3/movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Single<Response<MovieInfo>>

}