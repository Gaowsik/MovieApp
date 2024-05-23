package com.example.movieapp.ui.planetDetail

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val addFavouriteUseCase: AddFavouriteUseCase
) : ViewModel() {

    lateinit var movieDetailLiveData: MutableLiveData<Resource<Movie>>


    init {
        initStateLiveData()
    }

    private fun initStateLiveData() {
        movieDetailLiveData = MutableLiveData<Resource<Movie>>()
    }


    fun getMovieDetailById(
        id: Int
    ) = viewModelScope.launch {
        movieDetailLiveData.setLoading()
            movieRepository.getMovieById(id).catch {
                movieDetailLiveData.setErrorString((it.message.toString()))
            }.collect(){
                movieDetailLiveData.setSuccess(data = it.data, message = null)
            }
    }

    fun addFavouriteById(id: Int) = viewModelScope.launch {
        val updateTaxiStatusResult =
            addFavouriteUseCase(id)
        if (updateTaxiStatusResult != 1) throw IllegalStateException("Failed to On Local!")
    }

}