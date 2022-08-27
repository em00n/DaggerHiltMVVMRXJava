package com.emon.dagger_hilt_mvvm_rxjava.ui.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.emon.dagger_hilt_mvvm_rxjava.databinding.FragmentMovieListBinding
import com.emon.dagger_hilt_mvvm_rxjava.ui.view.BaseFragment
import com.emon.dagger_hilt_mvvm_rxjava.utils.autoCleared
import com.emon.dagger_hilt_mvvm_rxjava.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

class MovieListFragment : BaseFragment(), MovieItemClickListener {
    private var binding: FragmentMovieListBinding by autoCleared()
    private val movieViewModel by viewModels<MovieViewModel>()
    lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieListAdapter = MovieListAdapter(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        binding.movieListRV.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieListAdapter
        }

        getMoviesList()

        return binding.root
    }

    override fun onMovieItemClick(id: Int) {
        requireView().findNavController()
            .navigate(MovieListFragmentDirections.actionMovielistFragmentToMovieDetailsFragment(id))
    }

    private fun getMoviesList() {
        lifecycleScope.launch {
            movieViewModel.getMoviesList().observe(viewLifecycleOwner) {
                it.let {
                    movieListAdapter.submitData(lifecycle, it)
                }
            }
        }
    }

}