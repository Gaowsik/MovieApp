package com.example.movieapp.ui.planetList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.Movie
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.utills.state_models.Resource
import com.example.movieapp.data.utills.state_models.setErrorString
import com.example.movieapp.data.utills.state_models.setLoading
import com.example.movieapp.data.utills.state_models.setSuccess
import com.example.movieapp.domain.AddFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val addFavouriteUseCase: AddFavouriteUseCase
) : ViewModel() {
    lateinit var myMoviesListLiveData: MutableLiveData<Resource<List<Movie>>>

    init {
        initStateLiveData()
    }

    private fun initStateLiveData() {
        myMoviesListLiveData = MutableLiveData<Resource<List<Movie>>>()
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

    fun addFavouriteById(id: Int) = viewModelScope.launch {
        val updateTaxiStatusResult =
            addFavouriteUseCase(id)
        if (updateTaxiStatusResult != 1) throw IllegalStateException("Failed to On Local!")
    }

    fun refreshMoviesList() {
        viewModelScope.launch {
                movieRepository.refreshMovies()

        }
    }


}