package com.example.movieapp.ui.planetList

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.utills.state_models.Resource
import com.example.movieapp.data.utills.state_models.ResourceState
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.Constants.BUNDLE_TRACK_ID
import com.example.movieapp.ui.Constants.QUERY_VALUE_COUNTRY
import com.example.movieapp.ui.Constants.QUERY_VALUE_MEDIA
import com.example.movieapp.ui.Constants.QUERY_VALUE_TERM
import com.example.movieapp.ui.adapter.MovieListRecycleAdapter
import com.example.movieapp.ui.extentions.startActivity
import com.example.movieapp.ui.planetDetail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val movieListViewModel: MovieListViewModel by viewModels()
    private var progressDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

    }

    private fun init() {
        bindUi()
        setUpObservers()
        // initProgressDialog()
        movieListViewModel.refreshMoviesList(
            QUERY_VALUE_TERM, QUERY_VALUE_COUNTRY, QUERY_VALUE_MEDIA
        )


    }

    private fun bindUi() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    private fun setUpObservers() {
        movieListViewModel.myMoviesListLiveData.observe(
            this
        ) { observeGetMoviesListRequest(it) }
    }


    private fun setMovieLisRecycleView(movieList: List<Movie>) {
        movieList.let { moviesListResponse ->
            binding.recyclerviewMovieList.layoutManager = LinearLayoutManager(this@MainActivity)
            val movieListRecycleAdapter =
                MovieListRecycleAdapter(moviesListResponse, onFavouriteClicked = { trackId ->
                    handleFavouriteItemClick(trackId)

                }) { trackId ->
                    handleItemClick(trackId)
                }
            binding.recyclerviewMovieList.adapter = movieListRecycleAdapter
        }
    }

    private fun handleItemClick(trackId: Int) {
        navigateToDetailMovieScreen(trackId)
    }

    private fun handleFavouriteItemClick(trackId: Int) {
        makeMovieFavourite(trackId)
    }

    private fun observeGetMoviesListRequest(resource: Resource<List<Movie>>?) {
        when (resource?.state) {
            ResourceState.LOADING -> {

            }

            ResourceState.SUCCESS -> {
                resource.data?.let { movieList ->
                    handleMoviesListResponse(movieList = movieList)
                }
            }

            ResourceState.ERROR -> {

            }

            else -> {}
        }
    }

    private fun handleMoviesListResponse(movieList: List<Movie>) {
        setMovieLisRecycleView(movieList)
    }

    private fun navigateToDetailMovieScreen(trackId: Int) {
        startActivity<MovieDetailActivity> {
            putExtra(BUNDLE_TRACK_ID, trackId)
            Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    }

    private fun makeMovieFavourite(id: Int) {
        movieListViewModel.addFavouriteById(id)
    }

    override fun onResume() {
        super.onResume()
        movieListViewModel.getMovies()
    }


}
