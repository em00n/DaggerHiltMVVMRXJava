package com.emon.dagger_hilt_mvvm_rxjava.ui.movie_list

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.emon.dagger_hilt_mvvm_rxjava.BuildConfig.API_KEY
import com.emon.dagger_hilt_mvvm_rxjava.model.movie.MovieInfo
import com.emon.dagger_hilt_mvvm_rxjava.model.movie.MovieListResponse
import com.emon.dagger_hilt_mvvm_rxjava.network.ApiService
import com.emon.dagger_hilt_mvvm_rxjava.utils.Constants.language
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MovieListPageSource(private val apiService: ApiService) :
    RxPagingSource<Int, MovieInfo>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieInfo>> {
        val position = params.key ?: 1

        return apiService.getMoviesList(API_KEY, language, position)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: MovieListResponse, position: Int): LoadResult<Int, MovieInfo> {
        return LoadResult.Page(
            data = data.movieInfo,
            prevKey = null,
            nextKey = if (data.movieInfo.isEmpty()) null else position + 1
        )
    }


    override fun getRefreshKey(state: PagingState<Int, MovieInfo>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}