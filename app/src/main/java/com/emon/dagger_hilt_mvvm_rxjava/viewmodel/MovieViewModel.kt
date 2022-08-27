package com.emon.dagger_hilt_mvvm_rxjava.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.emon.dagger_hilt_mvvm_rxjava.R
import com.emon.dagger_hilt_mvvm_rxjava.model.movie.MovieInfo
import com.emon.dagger_hilt_mvvm_rxjava.repository.MovieRepository
import com.emon.dagger_hilt_mvvm_rxjava.utils.NetworkHelper
import com.emon.dagger_hilt_mvvm_rxjava.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val networkHelper: NetworkHelper,
    @ApplicationContext private val context: Context
) : ViewModel() {

    lateinit var movieInfoData: MutableLiveData<Resource<MovieInfo>>
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {

    }

    fun getMovieDetails(id: Int): LiveData<Resource<MovieInfo>> {
        movieInfoData = MutableLiveData<Resource<MovieInfo>>()
        if (networkHelper.isNetworkConnected()) {
            compositeDisposable.add(

                movieRepository.getMovieDetails(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { movieInfoData.postValue(Resource.loading(null)) }
                    .subscribe(
                        { response: Response<MovieInfo> ->
                            try {
                                if (response.isSuccessful) {
                                    movieInfoData.postValue(Resource.success(response.body()))

                                } else movieInfoData.postValue(
                                    Resource.error(
                                        response.code().toString(), null
                                    )
                                )
                            } catch (e: Exception) {
                            }

                        },
                        {
                            movieInfoData.postValue(Resource.error(it.message.toString(), null))
                        }
                    )
            )
        } else movieInfoData.postValue(
            Resource.error(
                context.getString(R.string.no_internet_connection),
                null
            )
        )
        return movieInfoData
    }

    fun getMoviesList(): LiveData<PagingData<MovieInfo>> {
        return movieRepository.getMoviesList()

    }
}