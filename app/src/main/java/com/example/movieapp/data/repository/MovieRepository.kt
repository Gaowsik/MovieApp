package com.example.movieapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.models.Movie
import com.example.movieapp.data.utills.state_models.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): List<Movie>
    fun getMovieById(movieId: String): Movie
    suspend fun refreshMovies()
    suspend fun addFavouriteById(movieId: Int) : Int
}