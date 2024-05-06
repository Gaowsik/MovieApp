package com.example.movieapp.ui.planetList

import android.app.Dialog
import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
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
        setUpListeners()
    }

    private fun bindUi() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    private fun setUpObservers() {
        movieListViewModel.myMoviesListLiveData.observe(
            this
        ) { observeGetMoviesListRequest(it) }

        movieListViewModel.filteredMoviesLiveData.observe(
            this
        ) { observeFilterMoviesListRequest(it) }

        movieListViewModel.isRefreshedLiveData.observe(
            this
        ) { observeIsRefreshedMoviesListRequest(it) }
    }

    private fun setUpListeners() {
        binding.editSearchMovie.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Call filter method in ViewModel when text changes
                movieListViewModel.filterMovies(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed
            }
        })
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
                    if (movieList.isEmpty()) {
                        getMovieListFromApi(
                            QUERY_VALUE_TERM,
                            QUERY_VALUE_COUNTRY,
                            QUERY_VALUE_MEDIA
                        )
                    } else {
                        handleMoviesListResponse(movieList = movieList)
                    }
                }
            }

            ResourceState.ERROR -> {

            }

            else -> {}
        }
    }

    private fun observeFilterMoviesListRequest(resource: Resource<List<Movie>>?) {

        when (resource?.state) {
            ResourceState.LOADING -> {

            }

            ResourceState.SUCCESS -> {
                resource.data?.let { filterList ->
                    handleMoviesFilterListResponse(filterList = filterList)
                }
            }

            ResourceState.ERROR -> {
                showEmptyText()
            }

            else -> {}
        }

    }


    private fun observeIsRefreshedMoviesListRequest(resource: Resource<Boolean>) {
        when (resource?.state) {
            ResourceState.LOADING -> {

            }

            ResourceState.SUCCESS -> {
                resource.data?.let { it ->
                    movieListViewModel.getMovies()
                }
            }

            ResourceState.ERROR -> {
                showEmptyText()
            }

            else -> {}
        }

    }

    private fun handleMoviesFilterListResponse(filterList: List<Movie>) {
        showRecyclerview()
        binding.recyclerviewMovieList?.adapter?.let {
            (it as MovieListRecycleAdapter).updateFilteredList(filterList)
        }

    }

    private fun getMovieListFromApi(term: String, country: String, media: String) {
        movieListViewModel.refreshMoviesList(term, country, media)
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

    private fun showRecyclerview() {
        binding.textEmpty.visibility = View.GONE
        binding.recyclerviewMovieList.visibility = View.VISIBLE
    }

    private fun showEmptyText() {
        binding.textEmpty.visibility = View.VISIBLE
        binding.recyclerviewMovieList.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        movieListViewModel.getMovies()
    }


}
