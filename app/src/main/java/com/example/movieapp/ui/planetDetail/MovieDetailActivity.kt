package com.example.movieapp.ui.planetDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.utills.state_models.Resource
import com.example.movieapp.data.utills.state_models.ResourceState
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.Constants.BUNDLE_TRACK_ID
import com.example.movieapp.ui.extentions.setVectorDrawableColor
import com.example.movieapp.ui.planetList.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private var itemTrackId : Int?=null
    private var selectTrackId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

    }

    private fun init() {
        bindUi()
        readArguments()
        setUpObservers()
        setUpListener()
    }


    private fun bindUi() {
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun readArguments() {
        intent.getIntExtra(BUNDLE_TRACK_ID, 0)
            ?.also { trackId ->
                itemTrackId = trackId
                getMovieDetail(trackId)
            }
    }

    private fun setUpObservers() {
        movieDetailViewModel.movieDetailLiveData.observe(
            this
        ) { observeGetMovieDetailRequest(it) }
    }

    private fun setUpListener() {
        binding.imgFavourite.setOnClickListener {
            itemTrackId?.let { it1 -> makeMovieFavourite(it1) }
            itemTrackId?.let { it1 -> getMovieDetail(it1) }
        }
    }


    private fun getMovieDetail(id: Int) {
        movieDetailViewModel.getMovieDetailById(id)
    }


    private fun observeGetMovieDetailRequest(resource: Resource<Movie>) {
        when (resource?.state) {
            ResourceState.LOADING -> {

            }

            ResourceState.SUCCESS -> {
                resource.data?.let { movie ->
                    handleMoviesDetailResponse(movie = movie)
                }
            }

            ResourceState.ERROR -> {

            }

            else -> {}
        }
    }

    private fun handleMoviesDetailResponse(movie: Movie) {
        binding.movie = movie
        Glide.with(this)
            .load(movie.artworkUrl100)
            .placeholder(R.drawable.ic_profile)
            .into(binding.imgMovie)
        updateFavoriteIconColor(movie.isFavourite)
    }


    private fun updateFavoriteIconColor(isFavourite: Boolean) {
        val colorResId =
            if (isFavourite) com.example.movieapp.R.color.colorRed else android.R.color.white
        val favoriteIconDrawable = setVectorDrawableColor(
            com.example.movieapp.R.drawable.ic_favourite,
            colorResId
        )
        binding.imgFavourite.setImageDrawable(favoriteIconDrawable)
    }

    private fun makeMovieFavourite(id: Int) {
        movieDetailViewModel.addFavouriteById(id)
    }
}