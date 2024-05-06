package com.example.movieapp.ui.adapter

import android.R
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.extentions.setVectorDrawableColor


class MovieListRecycleAdapter(
    var movieList: List<Movie>,
    var onFavouriteClicked: (Int) -> Unit,
    var onItemClicked: (Int) -> Unit
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

            itemView.setOnClickListener {
                this@MovieListRecycleAdapter.onItemClicked.invoke(movie.trackId)
            }

            updateFavoriteIconColor(movie.isFavourite)

            mBinding.imgFavourite.setOnClickListener {
                this@MovieListRecycleAdapter.onFavouriteClicked.invoke(movie.trackId)
                val drawable = itemView.context.setVectorDrawableColor(
                    com.example.movieapp.R.drawable.ic_favourite,
                    com.example.movieapp.R.color.colorRed
                )
                mBinding.imgFavourite.setImageDrawable(drawable)
            }
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

        private fun updateFavoriteIconColor(isFavourite: Boolean) {
            val colorResId =
                if (isFavourite) com.example.movieapp.R.color.colorRed else android.R.color.white
            val favoriteIconDrawable = itemView.context.setVectorDrawableColor(
                com.example.movieapp.R.drawable.ic_favourite,
                colorResId
            )
            mBinding.imgFavourite.setImageDrawable(favoriteIconDrawable)
        }
    }


}