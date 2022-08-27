package com.emon.dagger_hilt_mvvm_rxjava.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.emon.dagger_hilt_mvvm_rxjava.databinding.FragmentMovieDetailsBinding
import com.emon.dagger_hilt_mvvm_rxjava.ui.view.BaseFragment
import com.emon.dagger_hilt_mvvm_rxjava.utils.Constants
import com.emon.dagger_hilt_mvvm_rxjava.utils.Resource
import com.emon.dagger_hilt_mvvm_rxjava.utils.autoCleared
import com.emon.dagger_hilt_mvvm_rxjava.viewmodel.MovieViewModel

class MovieDetailsFragment : BaseFragment() {
    private var binding: FragmentMovieDetailsBinding by autoCleared()
    private val movieViewModel by viewModels<MovieViewModel>()

    var movieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt("com_emon_dagger_hilt_mvvm_coroutines_movie_id")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        movieId?.let { it1 -> getMovieDetails(it1) }

        return binding.root
    }

    private fun getMovieDetails(movieId: Int) {

        movieViewModel.getMovieDetails(movieId).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    try {
                        it.data.let { it2 ->
                            binding.movieTitle.text = "Title : ${it2?.title}"
                            binding.movieLanguage.text = "Language : ${it2?.originalLanguage}"
                            binding.movieOverview.text = it2?.overview
                            binding.movieImage.load(Constants.BASE_URL_IMAGE + it2?.posterPath)
                        }

                    } catch (e: Exception) {
                    }

                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {
                    showToast(it.message)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        movieViewModel.compositeDisposable.dispose()

    }
}