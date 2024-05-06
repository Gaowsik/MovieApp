package com.example.movieapp.ui.planetList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.utills.state_models.Resource
import com.example.movieapp.data.utills.state_models.setErrorString
import com.example.movieapp.data.utills.state_models.setLoading
import com.example.movieapp.data.utills.state_models.setSuccess
import com.example.movieapp.domain.AddFavouriteUseCase
import com.example.movieapp.ui.Constants.ACTION_SEARCH_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val addFavouriteUseCase: AddFavouriteUseCase
) : ViewModel() {
    lateinit var myMoviesListLiveData: MutableLiveData<Resource<List<Movie>>>
    lateinit var filteredMoviesLiveData: MutableLiveData<Resource<List<Movie>>>
    lateinit var isRefreshedLiveData: MutableLiveData<Resource<Boolean>>
    private val _internalMoviesListLiveData = MutableLiveData<Resource<List<Movie>>>()


    init {
        initStateLiveData()
    }

    private fun initStateLiveData() {
        myMoviesListLiveData = MutableLiveData<Resource<List<Movie>>>()
        filteredMoviesLiveData = MutableLiveData<Resource<List<Movie>>>()
        isRefreshedLiveData = MutableLiveData<Resource<Boolean>>()
    }


    fun getMovies(
    ) = viewModelScope.launch {
        myMoviesListLiveData.setLoading()
        try {
            val moviesList = movieRepository.getMovies()
            myMoviesListLiveData.setSuccess(data = moviesList, message = null)
        } catch (e: Exception) {
            myMoviesListLiveData.setErrorString((e.toString()))
            e.printStackTrace()
            return@launch
        }
    }

    /**
     * this is created to get the latest db value
     */
    fun getLatestMovies(
    ) = viewModelScope.launch {
        _internalMoviesListLiveData.setLoading()
        try {
            val moviesList = movieRepository.getMovies()
            _internalMoviesListLiveData.setSuccess(data = moviesList, message = null)
        } catch (e: Exception) {
            _internalMoviesListLiveData.setErrorString((e.toString()))
            e.printStackTrace()
            return@launch
        }
    }

    fun addFavouriteById(id: Int) = viewModelScope.launch {
        val updateTaxiStatusResult =
            addFavouriteUseCase(id)
        if (updateTaxiStatusResult != 1) throw IllegalStateException("Failed to On Local!")
    }


    fun refreshMoviesList(
        term: String, country: String, media: String
    ) = viewModelScope.launch {
        isRefreshedLiveData.setLoading()
        try {
            movieRepository.refreshMovies(term, country, media)
            isRefreshedLiveData.setSuccess(true)
        } catch (e: Exception) {
            isRefreshedLiveData.setErrorString((e.toString()))
            e.printStackTrace()
            return@launch
        }
    }

    fun filterMovies(query: String) {
        viewModelScope.launch {
            delay(ACTION_SEARCH_DELAY)
            filteredMoviesLiveData.setLoading()

            val moviesJob = async { getLatestMovies() }
            // Wait for the getMovies job to complete
            moviesJob.await()
            val currentList =
                _internalMoviesListLiveData.value?.data ?: emptyList()
            val filteredList = if (query.isBlank()) {
                currentList
            } else {
                currentList.filter { movie ->
                    movie.trackName.contains(query, true)
                }
            }

            if (filteredList.isNotEmpty()) {
                filteredMoviesLiveData.setSuccess(filteredList, message = null)
            } else {
                filteredMoviesLiveData.setErrorString("No results found")
            }
        }
    }


}