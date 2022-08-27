package com.emon.dagger_hilt_mvvm_rxjava.ui.movie_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.emon.dagger_hilt_mvvm_rxjava.databinding.RowMovieItemBinding
import com.emon.dagger_hilt_mvvm_rxjava.model.movie.MovieInfo
import com.emon.dagger_hilt_mvvm_rxjava.utils.Constants

class MovieListAdapter(private val movieItemClickListener: MovieItemClickListener) :
    PagingDataAdapter<MovieInfo, MovieListAdapter.MovieListViewHolder>(
        Diff()
    ) {

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movieInfo = getItem(position)
        if (movieInfo != null) {
            holder.binds(movieInfo, movieItemClickListener)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowMovieItemBinding.inflate(layoutInflater, parent, false)
        return MovieListViewHolder(binding)

    }

    class MovieListViewHolder(private val binding: RowMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binds(
            data: MovieInfo,
            movieItemClickListener: MovieItemClickListener
        ) {
            binding.apply {
                movieTitle.text = data.title
                data.posterPath.let {
                    movieImage.load(Constants.BASE_URL_IMAGE + it) {
                    }
                }
                itemView.setOnClickListener {
                    movieItemClickListener.onMovieItemClick(data.id)
                }

            }
        }
    }

    class Diff : DiffUtil.ItemCallback<MovieInfo>() {
        override fun areItemsTheSame(
            oldItem: MovieInfo,
            newItem: MovieInfo
        ): Boolean =
            oldItem.title == newItem.title

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: MovieInfo,
            newItem: MovieInfo
        ): Boolean =
            oldItem == newItem
    }
}