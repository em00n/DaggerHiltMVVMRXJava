package com.emon.dagger_hilt_mvvm_rxjava.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.emon.dagger_hilt_mvvm_rxjava.BuildConfig
import com.emon.dagger_hilt_mvvm_rxjava.network.ApiService
import com.emon.dagger_hilt_mvvm_rxjava.ui.movie_list.MovieListPageSource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getMovieDetails(id: Int) = apiService.getMovieDetails(id, BuildConfig.API_KEY)

    fun getMoviesList() =
        Pager(PagingConfig(pageSize = 10)) {
            MovieListPageSource(apiService)
        }.liveData

}