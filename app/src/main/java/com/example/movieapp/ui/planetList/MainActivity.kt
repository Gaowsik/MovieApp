package com.example.movieapp.ui.planetList

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.utills.state_models.Resource
import com.example.movieapp.data.utills.state_models.ResourceState
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.Constants.QUERY_VALUE_COUNTRY
import com.example.movieapp.ui.Constants.QUERY_VALUE_MEDIA
import com.example.movieapp.ui.Constants.QUERY_VALUE_TERM
import com.example.movieapp.ui.adapter.MovieListRecycleAdapter
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
        //  movieListViewModel.addFavouriteById(208510932)
        movieListViewModel.getMovies()

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
            val movieListRecycleAdapter = MovieListRecycleAdapter(moviesListResponse)
            binding.recyclerviewMovieList.adapter = movieListRecycleAdapter
        }
    }

    private fun observeGetMoviesListRequest(resource: Resource<List<Movie>>?) {
        when (resource?.state) {
            ResourceState.LOADING -> {

            }

            ResourceState.SUCCESS -> {
                hideProgressDialog()
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

    /*    private fun initProgressDialog() {
            progressDialog = Dialog(this).apply {
                this.setContentView(R.layout.progress_dialog)
                this.setCancelable(false)
                this.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }*/

    /**
     * show Progress Dialog
     */
    fun showProgressDialog() {
        progressDialog?.show()
    }


    /**
     * hide Progress dialog
     */
    fun hideProgressDialog() {
        progressDialog?.dismiss()
    }
}
