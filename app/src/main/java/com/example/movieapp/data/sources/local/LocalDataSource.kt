package com.example.movieapp.data.sources.local

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.models.Movie
import com.example.movieapp.data.utills.state_models.Resource
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getMovies(): List<Movie>
    fun getMovieById(planetId: Int): Movie
    suspend fun setPlanets(planets: List<Movie>)
    suspend fun addFavouriteById(id: Int) : Int
}