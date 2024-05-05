package com.example.movieapp.ui.adapter

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.domain.model.Movie


class MovieListRecycleAdapter(
    var movieList: List<Movie>,
) :
    RecyclerView.Adapter<MovieListRecycleAdapter.MoviesListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListRecycleAdapter.MoviesListViewHolder {
        val inflate = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoviesListViewHolder(inflate)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) =
        holder.run {
            val movieResponse = movieList[position]
            onBind(movie = movieResponse)
        }

    inner class MoviesListViewHolder(private val mBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun onBind(movie: Movie) {
            bindDataToUi(movie = movie)
        }

        private fun bindDataToUi(movie: Movie) {
            mBinding.textTrackName.text = movie.trackName
            mBinding.textGenre.text = movie.primaryGenreName
            mBinding.textPrice.text = movie.trackPrice.toString()
            // Load image using Glide
            Glide.with(itemView.context)
                .load(movie.artworkUrl100)
                .into(mBinding.imgMovie)

        }
    }
}